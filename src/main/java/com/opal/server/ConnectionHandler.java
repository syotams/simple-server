package com.opal.server;

import com.opal.server.protocol.Protocol;
import com.opal.server.request.Request;
import com.opal.server.request.RequestHandlerInterface;
import com.opal.server.response.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionHandler implements ConnectionHandlerInterface {

    private final Protocol protocol;

    private ArrayList<RequestHandlerInterface> requestHandlers = new ArrayList<>();


    public ConnectionHandler(Protocol protocol) {
        this.protocol = protocol;
    }

    public void onConnection(Socket socket) {
        try(
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream out  = socket.getOutputStream()
        ) {
            socket.setSoTimeout(3000);

            Request request = protocol.process(in);
            Response response = Response.build(out);

            for (RequestHandlerInterface requestHandler : requestHandlers) {
                // TODO: should handle exception and eliminate predicate
                if(requestHandler.handle(request, response)) {
                    break;
                }
            }

            response.send();
            socket.close();

        } catch (IOException e) {
            // we set catch here if socket is closed by client, so server won't be closed
            System.out.println("Connection closed by client");
        }
    }

    @Override
    public void addRequestHandler(RequestHandlerInterface requestHandler) {
        requestHandlers.add(requestHandler);
    }
}
