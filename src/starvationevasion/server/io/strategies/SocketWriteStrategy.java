package starvationevasion.server.io.strategies;



import starvationevasion.server.io.strategies.AbstractWriteStrategy;

import java.io.*;
import java.net.Socket;

public class SocketWriteStrategy extends AbstractWriteStrategy<String>
{

  public SocketWriteStrategy (Socket socket)
  {
    super(socket);
  }

  public SocketWriteStrategy (Socket socket, DataOutputStream stream)
  {
    super(socket, stream);
  }

  @Override
  public void write (String s) throws IOException
  {
    getStream().writeUTF(s);
    getStream().flush();
  }
  
  @Override
  public void close () throws IOException
  {
    // socket.shutdownOutput();
  }

}