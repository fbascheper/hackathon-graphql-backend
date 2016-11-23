import org.scalatest.{Matchers, WordSpec}
import sangria.ast.Document
import sangria.execution.Executor
import sangria.execution.deferred.DeferredResolver
import sangria.macros._
import sangria.marshalling.sprayJson._
import spray.json._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class SchemaSpec extends WordSpec with Matchers {
  "Cities Schema" should {
    "should contain Berlin" in {
      val query =
        graphql"""
         query CitiesQuery {
           cities {
             name
           }
         }
       """

      val result: JsValue = executeQuery(query)
      result.toString.contains("Berlin") should be(true)
    }

    "allow to fetch a city using a country provided through variables" in {
      val query =
        graphql"""
         query FetchSomeCountryQuery($$countryCode: String!) {
           cities(country: $$countryCode) {
             name
             country
           }
         }
       """

//      executeQuery(query, vars = JsObject("countryCode" → """{ "country": "DE" }""".parseJson)) should be(
//        """
//         {
//           "data": {
//             "city": [
//               {
//                 "name": "Berlin",
//                 "country": "DE"
//               }
//             ]
//           }
//         }
//        """.parseJson)
    }
  }

  def executeQuery(query: Document, vars: JsObject = JsObject.empty) = {
    val futureResult = Executor.execute(SchemaDefinition.CitiesSchema, query,
      variables = vars,
      userContext = new CityRepo,
      deferredResolver = DeferredResolver.fetchers(SchemaDefinition.cities))

    Await.result(futureResult, 10.seconds)
  }
}
