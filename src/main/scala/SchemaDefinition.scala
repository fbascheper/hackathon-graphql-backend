import sangria.execution.deferred.{Fetcher, HasId}
import sangria.schema.{Field, _}

import scala.concurrent.Future

/**
  * Defines a GraphQL schema for the current project
  */
object SchemaDefinition {
  /**
    * Resolves the lists of cities. These resolutions are batched and cached for the duration of a query.
    */
  val cities = Fetcher.caching(
    (ctx: CityRepo, ids: Seq[Int]) ⇒
      Future.successful(ids.flatMap(id ⇒ ctx.getCity(id))))(HasId(_.id))

  val CountryEnum = EnumType(
    "Country",
    Some("A country inside Europe"),
    List(
      EnumValue("NL",
        value = Country.NL,
        description = Some("Nederland")),
      EnumValue("BE",
        value = Country.BE,
        description = Some("België")),
      EnumValue("DE",
        value = Country.DE,
        description = Some("Duitsland")),
      EnumValue("ES",
        value = Country.ES,
        description = Some("Spanje")),
      EnumValue("FR",
        value = Country.FR,
        description = Some("Frankrijk")),
      EnumValue("IT",
        value = Country.IT,
        description = Some("Italië")),
      EnumValue("PT",
        value = Country.PT,
        description = Some("Portugal"))))

  val Forecast = ObjectType(
    "Forecast",
    "The forecast for a given day",
    interfaces[CityRepo, WeatherForecast](),
    fields[CityRepo, WeatherForecast](
      Field("daysAhead", IntType,
        Some("The number of days ahead"),
        resolve = _.value.daysAhead
      ),
      Field("maxTemp", FloatType,
        Some("The maximum temperature"),
        resolve = _.value.maxTemp
      )
    )
  )

  val GpsCoordinates = ObjectType(
    "gps_coordinates",
    "The gps coordinates for a given city",
    interfaces[CityRepo, Coordinates](),
    fields[CityRepo, Coordinates](
      Field("lon", FloatType,
        Some("The longitude"),
        resolve = _.value.lon
      ),
      Field("lat", FloatType,
        Some("The latitude"),
        resolve = _.value.lat
      )
    )
  )


  val City =
    ObjectType(
      "City",
      "A city inside Europe.",
      interfaces[CityRepo, City](),
      fields[CityRepo, City](
        Field("id", IntType,
          Some("The id of the city."),
          resolve = _.value.id),
        Field("name", OptionType(StringType),
          Some("The name of the city."),
          resolve = _.value.name),
        Field("country", OptionType(CountryEnum),
          Some("The country of the city."),
          resolve = _.value.country),
        Field("gpscoords", OptionType(GpsCoordinates),
          Some("The GPS coordinates of the city."),
          resolve = _.value.coordinates),
        Field("forecast", ListType(OptionType(Forecast)),
          Some("The weatherforecast for this city."),
          resolve = _.value.forecast)
      ))


  val ID = Argument("id", StringType, description = "id of the character")

  val CountryArg = Argument("country", OptionInputType(CountryEnum),
    description = "Restrict cities to a given country")

  val Query = ObjectType(
    "Query", fields[CityRepo, Unit](
      Field("cities", ListType(City),
        arguments = CountryArg :: Nil,
        resolve = (ctx) ⇒ ctx.ctx.getCities(ctx.arg(CountryArg)))
    ))

  val CitiesSchema = Schema(Query)
}
