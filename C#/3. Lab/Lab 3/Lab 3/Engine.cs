using System.Xml.Serialization;

namespace Lab_3;

[XmlRoot(ElementName = "engine")]
public class Engine
{
    public double Displacement;
    public double HorsePower;
    [XmlAttribute(AttributeName = "model")]
    public string Model;

    public Engine(){}
    public Engine(double displacement, double horsePower, string model)
    {
        this.Displacement = displacement;
        this.HorsePower = horsePower;
        this.Model = model;
    }
}