const express = require('express')
const app = express()
const port = 1234
const milliseconds = 6 * 1000;

const AWS = require('aws-sdk')
AWS.config.update({ region: 'eu-west-1' });

app.get('/isalive', async (req, res) => {
  res.sendStatus(200);
})

app.get('/', async (req, res) => {
  res.send(await readAllTables())
})

app.listen(port, () => {
  console.log(`listening on port ${port}`)
})

const readAllTables = async() => {
  var dynamodb = new AWS.DynamoDB();

  const date = Date.now();
  let currentDate = null;
  do {
    currentDate = Date.now();
  } while (currentDate - date < milliseconds);

  var params = {};
  var tables = [];

  while(true) {
    var response = await dynamodb.listTables(params).promise();
    tables = tables.concat(response.TableNames);

    if (undefined === response.LastEvaluatedTableName) {
      break;
    } else {
      params.ExclusiveStartTableName = response.LastEvaluatedTableName;
    }
  }

  console.log(tables);

  return tables;
}