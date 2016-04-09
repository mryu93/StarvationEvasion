package starvationevasion.server.model;

/**
 * @author Javier Chavez (javierc@cs.unm.edu)
 */

import com.oracle.javafx.jmx.json.JSONDocument;
import starvationevasion.common.EnumPolicy;
import starvationevasion.common.EnumRegion;
import starvationevasion.server.Worker;
import starvationevasion.server.io.NotImplementedException;

import java.beans.Transient;
import java.util.ArrayList;

public class User implements Encryptable, Sendable
{
  public transient int actionsRemaining = 2;
  public transient Worker worker;
  private transient String salt;

  private String username = "Anon";
  private String password = "";
  private EnumRegion region;
  private boolean isLoggedIn = false;
  private boolean isPlaying = false;
  private ArrayList<EnumPolicy> hand = new ArrayList<>();
  public int policyCardsDiscarded = 0;

  public User()
  {
  }

  public User (String username, String password, EnumRegion region, ArrayList<EnumPolicy> hand)
  {
    this.username = username;
    this.hand = hand;
    this.region = region;
    encrypt(password, null);
  }


  public String getUsername ()
  {
    return username;
  }

  public void setUsername (String username)
  {
    this.username = username;
  }

  public String getPassword ()
  {
     return password;
  }

  public void setPassword (String password)
  {
    encrypt(password, null);
  }

  public void setEncryptedPassword (String password, String salt)
  {
     this.password = password;
     this.salt = salt;
  }

  public EnumRegion getRegion ()
  {
     return region;
  }

  public void setRegion (EnumRegion region)
  {
     this.region = region;
  }

  public ArrayList<EnumPolicy> getHand ()
  {
    return hand;
  }

  public void setHand (ArrayList<EnumPolicy> hand)
  {
    this.hand = hand;
  }

  public boolean isLoggedIn ()
  {
    return isLoggedIn;
  }

  public void setLoggedIn (boolean loggedIn)
  {
    isLoggedIn = loggedIn;
  }

  public boolean isPlaying ()
  {
    return isPlaying;
  }

  public void setPlaying (boolean playing)
  {
    isPlaying = playing;
  }

  @Transient
  public String getSalt ()
  {
    return salt;
  }

  @Transient
  public synchronized Worker getWorker ()
  {
    return worker;
  }
  @Transient
  public User setWorker (Worker worker)
  {
    this.worker = worker;
    return this;
  }

  @Override
  public String toString ()
  {
    return String.valueOf(region);
  }

  @Override
  public void encrypt (String pwd, String key)
  {
    if (key == null || key.isEmpty())
    {
      salt = Encryptable.generateKey();
    }
    password = Encryptable.generateHashedPassword(salt, pwd);
  }

  @Override
  public <T> T decrypt (String msg, String key)
  {
    throw new NotImplementedException();
  }


  @Override
  public Type getType ()
  {
    return Type.USER;
  }

  @Override
  public JSONDocument toJSON ()
  {
    JSONDocument json = new JSONDocument(JSONDocument.Type.OBJECT);

    json.setString("username", username);
    json.setString("password", password);
    json.setString("region", String.valueOf(region));
    json.setString("type", getType().name());

    JSONDocument _hand = JSONDocument.createArray(hand.size());
    for (int i = 0; i < hand.size(); i++)
    {
      _hand.set(i, hand.get(i).toJSON());
    }

    json.set("hand", _hand);

    return json;
  }

  @Override
  public void fromJSON (Object doc)
  {
    throw new NotImplementedException();
  }

}
