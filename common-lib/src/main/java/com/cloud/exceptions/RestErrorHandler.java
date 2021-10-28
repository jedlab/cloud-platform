package com.cloud.exceptions;

import java.io.IOException;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.UnknownHttpStatusCodeException;

/**
 * @author Omid Pourhadi
 *
 */
public class RestErrorHandler extends DefaultResponseErrorHandler
{
    private static final Logger logger = Logger.getLogger(RestErrorHandler.class.getName());

    @Override
    protected void handleError(ClientHttpResponse response, HttpStatus statusCode) throws IOException
    {
        logger.info("#########################");
        logger.info(""+response.getRawStatusCode());
        logger.info(statusCode.name());
        logger.info(IOUtils.toString(response.getBody()));
        logger.info("#########################");
        switch (statusCode.series())
        {
        case CLIENT_ERROR:
            throw new HttpClientErrorException(statusCode, response.getStatusText(), response.getHeaders(), getResponseBody(response),
                    getCharset(response));
        case SERVER_ERROR:
            throw new HttpServerErrorException(statusCode, response.getStatusText(), response.getHeaders(), getResponseBody(response),
                    getCharset(response));
        default:
            throw new UnknownHttpStatusCodeException(statusCode.value(), response.getStatusText(), response.getHeaders(),
                    getResponseBody(response), getCharset(response));
        }
    }
}