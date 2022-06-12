// See https://aka.ms/new-console-template for more information

using System.Xml.Linq;
using Lab_3;

List<Car> cars = new List<Car>()
{
  new Car("E250", new Engine(1.8, 204, "CGI"), 2009),
  new Car("E350", new Engine(3.5, 292, "CGI"), 2009),
  new Car("A6", new Engine(2.5, 187, "FSI"), 2012),
  new Car("A6", new Engine(2.8, 220, "FSI"), 2012),
  new Car("A6", new Engine(3.0, 295, "TFSI"), 2012),
  new Car("A6", new Engine(2.0, 175, "TDI"), 2011),
  new Car("A6", new Engine(3.0, 309, "TDI"), 2011),
  new Car("S6", new Engine(4.0, 414, "TFSI"), 2012),
  new Car("S8", new Engine(4.0, 513, "TFSI"), 2012)
};


var myAnonymousCars = cars
  .Where(s => s.Model.Equals("A6"))
  .Select(car => new
  {
    engineType = String.CompareOrdinal(car.Motor.Model, "TDI") == 0 ? "diesel" : "petrol",
    hppl = car.Motor.HorsePower / car.Motor.Displacement
  });
foreach (var elem in myAnonymousCars)
{
  Console.WriteLine(elem);
}

var groupedResults = myAnonymousCars
  .GroupBy(elem => elem.engineType)
  .Select(elem => $"{elem.First().engineType}: {elem.Average(s => s.hppl).ToString()}");
foreach (var result in groupedResults)
{
  Console.WriteLine(result);
}

  IEnumerable<XElement> nodes = cars.Select(n =>
    new XElement("car", new XElement("model", n.Model),
      new XElement("engine",
        new XAttribute("model", n.Motor.Model),
        new XElement("displacement", n.Motor.Displacement.ToString()),
        new XElement("horsePower", n.Motor.HorsePower.ToString())),
      new XElement("year", n.Year.ToString())));
  XElement rootNode = new XElement("cars", nodes);
  rootNode.Save("CarsCollectionLinq.xml");