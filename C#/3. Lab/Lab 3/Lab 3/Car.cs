using System.Xml.Serialization;

namespace Lab_3;

[XmlType("car")]
[XmlRoot("cars")]
public class Car
{
    public string Model;
    public int Year;
    [XmlElement(ElementName = "engine")]
    public Engine Motor;

    public Car(string model, Engine motor, int year)
    {
        Model = model;
        Year = year;
        Motor = motor;
    }
}