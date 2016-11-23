# hackathon-graphql-backend


## Running the server

After starting the server with

    sbt run

you can run queries interactively using [GraphiQL](https://github.com/graphql/graphiql) by opening http://localhost:8080 in a browser or query the `/graphql` endpoint. It accepts following properties in the JSON body (this follows [relay](https://facebook.github.io/relay) convention):

* `query` - String - GraphQL query as a string
* `variables` - Object or String containing a JSON object that defines variables for your query _(optional)_
* `operationName` - String - the name of the operation, in case you defined several of them in the query _(optional)_

Here are some examples of the queries you can make:

```bash
$ curl -X POST localhost:8080/graphql \
    -H "Content-Type:application/json" \
    -d '{"query": "{cities {name, country}}"}'
```

this gives back the cities and their countries:

```javascript
{
  "data": {
    "cities":[
      {"name":"Assen","country":"NL"},
      {"name":"Groningen","country":"NL"}
      ]
    }
  }
}
```

Here is another example, which uses variables:

```bash
$ curl -X POST localhost:8080/graphql \
    -H "Content-Type:application/json" \
    -d '{"query": "query Test($humanId: String!){human(id: $humanId) {name, homePlanet, friends {name}}}", "variables": {"humanId": "1000"}}'
```


```bash
$ curl -X POST localhost:8080/graphql \
    -H "Content-Type:application/json" \
    -d '{"query": "query Cities($country: Country!){cities(country: $country) {name, id, country, forecast {daysAhead, maxTemp}}}", "variables": {"country": "DE"}}'
```

query Cities($country: Country) {
  cities(country: $country) {
    name
    id
    country
    forecast  {
      daysAhead
      maxTemp
    }
  }
}


The result should be something like this:

```javascript
{
  "data": {
    "cities": [
      {
        "name": "Berlin",
        "id": 2950159,
        "country": "DE",
        "forecast":[
            {"daysAhead":0,"maxTemp":14.2},
            {"daysAhead":1,"maxTemp":12.2},
            {"daysAhead":2,"maxTemp":10.2},
            {"daysAhead":3,"maxTemp":8.2}]
      }
    ]
  }
} 
```

You can also run GraphiQL using [a query with parameters](http://localhost:8080/graphql?query=query%20City%20%7B%0A%20%20cities%20%7B%0A%20%20%20%20name%0A%20%20%20%20id%0A%20%20%20%20country%0A%20%20%20%20gpscoords%20%7B%0A%20%20%20%20%20%20lon%0A%20%20%20%20%20%20lat%0A%20%20%20%20%7D%0A%20%20%7D%0A%7D%0A)
or a [query with parameters](http://localhost:8080/?query=%23%20Welcome%20to%20GraphiQL%0A%23%0A%23%20GraphiQL%20is%20an%20in-browser%20IDE%20for%20writing%2C%20validating%2C%20and%0A%23%20testing%20GraphQL%20queries.%0A%23%0A%23%20Type%20queries%20into%20this%20side%20of%20the%20screen%2C%20and%20you%20will%0A%23%20see%20intelligent%20typeaheads%20aware%20of%20the%20current%20GraphQL%20type%20schema%20and%0A%23%20live%20syntax%20and%20validation%20errors%20highlighted%20within%20the%20text.%0A%23%0A%23%20To%20bring%20up%20the%20auto-complete%20at%20any%20point%2C%20just%20press%20Ctrl-Space.%0A%23%0A%23%20Press%20the%20run%20button%20above%2C%20or%20Cmd-Enter%20to%20execute%20the%20query%2C%20and%20the%20result%0A%23%20will%20appear%20in%20the%20pane%20to%20the%20right.%0A%0A%0Aquery%20Cities(%24country%3A%20Country)%20%7B%0A%20%20cities(country%3A%20%24country)%20%7B%0A%20%20%20%20name%0A%20%20%20%20id%0A%20%20%20%20country%0A%20%20%20%20forecast%20%20%7B%0A%20%20%20%20%20%20daysAhead%0A%20%20%20%20%20%20maxTemp%0A%20%20%20%20%7D%0A%20%20%7D%0A%7D&operationName=Cities&variables=%7B%0A%20%20%22country%22%3A%20%22NL%22%0A%7D)
.
