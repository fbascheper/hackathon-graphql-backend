import java.time.LocalDate

/**
  * hackathon-graphql-backend - Description.
  *
  * @author Erik-Berndt Scheper
  * @since 18-11-2016
  *
  */
object Country extends Enumeration {
  val NL, BE, DE, ES, FR, IT, PT = Value
}

case class WeatherForecast(date: LocalDate
                           , daysAhead: Int
                           , maxTemp: Double)


case class City(id: Int
                , name: String
                , country: Country.Value
                , coordinates: Coordinates
               ) {
  def isInNL: Boolean = country == Country.NL

  def forecast: Seq[Option[WeatherForecast]] = {
    def temp(daysAhead: Int) = country match {
      case Country.NL => 5.5 - daysAhead
      case Country.BE => 11.2 - daysAhead
      case Country.DE => 14.2 - daysAhead * 2
      case Country.ES => 20.2 + daysAhead * 0.2
      case Country.PT => 22.5 - daysAhead * 0.1
      case _ => 18.5 + daysAhead
    }

    def dayForecast(i: Int): Option[WeatherForecast] = {
      Some(WeatherForecast(LocalDate.now().plusDays(i), i, temp(i)))
    }

    Range(0, 4) map (i => dayForecast(i))
  }
}


case class Coordinates(lon: Double
                       , lat: Double
                      )

class CityRepo {

  import CityRepo._

  def getCities(ct : Option[Country.Value]) =
    cities filter (c => c.country == ct.getOrElse(c.country))

  def getCity(id: Int): Option[City] = cities.find(c ⇒ c.id == id)

  def getCity(name: String): Option[City] = cities.find(c ⇒ c.name == name)
}

object CityRepo {
  val cities = List(
    City(id = 2759632, name = "Assen", country = Country.NL, coordinates = Coordinates(6.55, 52.98333)),
    City(id = 2755250, name = "Groningen", country = Country.NL, coordinates = Coordinates(6.55, 53.200001)),
    City(id = 2751791, name = "Leeuwarden", country = Country.NL, coordinates = Coordinates(5.78333, 53.183331)),
    City(id = 2743476, name = "Zwolle", country = Country.NL, coordinates = Coordinates(6.09359, 52.512642)),
    City(id = 2756070, name = "Enschede", country = Country.NL, coordinates = Coordinates(6.88333, 52.216671)),
    City(id = 2759661, name = "Arnhem", country = Country.NL, coordinates = Coordinates(5.91111, 51.98)),
    City(id = 2759705, name = "Apeldoorn", country = Country.NL, coordinates = Coordinates(5.96667, 52.200001)),
    City(id = 2756252, name = "Eindhoven", country = Country.NL, coordinates = Coordinates(5.46667, 51.433331)),
    City(id = 2747351, name = "s-Hertogenbosch", country = Country.NL, coordinates = Coordinates(5.30417, 51.699169)),
    City(id = 2747350, name = "Den Bosch", country = Country.NL, coordinates = Coordinates(5.30056, 51.682671)),
    City(id = 2751282, name = "Maastricht", country = Country.NL, coordinates = Coordinates(5.7, 50.849998)),
    City(id = 2759794, name = "Amsterdam", country = Country.NL, coordinates = Coordinates(4.88969, 52.374031)),
    City(id = 5134453, name = "Rotterdam", country = Country.NL, coordinates = Coordinates(4.46667, 51.916672)),
    City(id = 2745912, name = "Utrecht", country = Country.NL, coordinates = Coordinates(5.12222, 52.090832)),
    City(id = 2750896, name = "Middelburg", country = Country.NL, coordinates = Coordinates(3.61389, 51.5)),
    City(id = 6544259, name = "Almere", country = Country.NL, coordinates = Coordinates(5.2375, 52.36861)),
    City(id = 2751737, name = "Lelystad", country = Country.NL, coordinates = Coordinates(5.48333, 52.466671)),
    City(id = 2950159, name = "Berlin", country = Country.DE, coordinates = Coordinates(13.41053, 52.524368)),
    City(id = 3337389, name = "Brussel", country = Country.BE, coordinates = Coordinates(4.34526, 50.851151)),
    City(id = 2988507, name = "Paris", country = Country.FR, coordinates = Coordinates(2.3488, 48.853409)),
    City(id = 3117732, name = "Madrid", country = Country.ES, coordinates = Coordinates(-3.69063, 40.425259)),
    City(id = 6458923, name = "Lissabon", country = Country.PT, coordinates = Coordinates(-9.14843, 38.726349)),
    City(id = 3173435, name = "Milaan", country = Country.IT, coordinates = Coordinates(9.18951, 45.464272)),
    City(id = 3169070, name = "Rome", country = Country.IT, coordinates = Coordinates(12.4839, 41.894741)))
}
