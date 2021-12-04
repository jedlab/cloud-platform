package com.cloud.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cloud.fs.CloudFileReader;

public class FileLoaderServlet extends HttpServlet {

	private WebApplicationContext springContext;

	private String repoHome;
	private CloudFileReader cfr;

	private LoadFileProperties loadFileProperties;

	public FileLoaderServlet(LoadFileProperties loadFileProperties) {
		this.loadFileProperties = loadFileProperties;
	}

	public void init(final ServletConfig config) throws ServletException {
		super.init(config);
		springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
		beanFactory.autowireBean(this);
		//
		this.repoHome = config.getInitParameter("repo");
		this.cfr = new CloudFileReader(repoHome);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String parameterEid = request.getParameter("eid");
		if(parameterEid == null)
		{
			String requestURI = request.getRequestURI();
	        if (requestURI.contains("/"))
	        {
	            String[] split = requestURI.split("/");
	            requestURI = split[split.length - 1];
	        }
	        parameterEid = requestURI;
		}
		long eid = Long.parseLong(parameterEid);
		InputStream is = null;
		OutputStream output = null;
		try {
			is = cfr.read(eid);
			if(is != null)
			{
				FileProperties props = loadFileProperties.getProps(eid, request);
				response.setContentType(props.getContentType());
				if(props.isInline())
					response.setHeader("Content-disposition", String.format("inline; filename=\"%s.jpg\"", props.getFileName()));
				else
					response.setHeader("Content-disposition", String.format("attachment; filename=\"%s.jpg\"", props.getFileName()));
				output = response.getOutputStream();
				IOUtils.copyLarge(is, output);
			}
		} finally {
			if(output != null)
				output.close();
			if(is != null)
				is.close();
		}
	}

}
