package persistence;

public class XmlConverterException extends Exception
{
  public XmlConverterException()
  {
    super();
  }

  public XmlConverterException(String message)
  {
    super(message);
  }

  public XmlConverterException(Exception e)
  {
    super(e);
  }

  public XmlConverterException(String message, Exception e)
  {
    super(message, e);
  }
}
