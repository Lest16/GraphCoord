import java.io.FileInputStream;
import java.util.Properties;

public class Params
{
    public double gamma;
    public int meanSpringLength;
    public double spring;
    public int q2;
    public int width;
    public int height;

    private FileInputStream fis;

    public Params() throws Exception
    {
        Properties property = new Properties();
        this.fis = new FileInputStream("src/main/resources/config");
        property.load(fis);
        this.gamma = Double.parseDouble(property.getProperty("gamma"));
        this.meanSpringLength = Integer.parseInt(property.getProperty("meanSpringLength"));
        this.spring = Double.parseDouble(property.getProperty("spring"));
        this.q2 = Integer.parseInt(property.getProperty("q2"));
        this.width = Integer.parseInt(property.getProperty("width"));
        this.height = Integer.parseInt(property.getProperty("height"));
    }
}
