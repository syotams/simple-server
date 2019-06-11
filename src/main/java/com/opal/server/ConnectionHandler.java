package com.opal.server;

import com.opal.server.protocol.Protocol;
import com.opal.server.request.RequestProcessor;
import com.opal.server.request.RequestProcessorInterface;
import com.opal.server.response.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ConnectionHandler implements ConnectionHandlerInterface {

    private final Protocol protocol;

    private RequestProcessor requestProcessor;


    public ConnectionHandler(Protocol protocol, RequestProcessor requestProcessor) {
        this.protocol = protocol;
        this.requestProcessor = requestProcessor;
    }

    public void onConnection(Socket socket) {
        try(
            BufferedReader in   = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream out     = socket.getOutputStream()
        ) {
            Response response = Response.build(out);
            requestProcessor.process(protocol.process(in), response);

            response.send();

        } catch (IOException e) {
            protocol.reset();
            // we set catch here if socket is closed by client, so server won't be closed
            System.out.println("Connection closed by client");
        }
    }

    @Override
    public void addRequestProcessor(RequestProcessorInterface requestProcessor) {
        this.requestProcessor.addRequestProcessor(requestProcessor);
    }
}
