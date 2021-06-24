import {inject, lifeCycleObserver, LifeCycleObserver} from '@loopback/core';
import {juggler} from '@loopback/repository';

const config = {
  name: 'EbankDB',
  connector: 'mongodb',
  url: 'mongodb://localhost:27017/ebankv1',
  host: 'localhost',
  port: 27017,
  user: '',
  password: '',
  database: 'ebankv1',
  useNewUrlParser: true
};

// Observe application's life cycle to disconnect the datasource when
// application is stopped. This allows the application to be shut down
// gracefully. The `stop()` method is inherited from `juggler.DataSource`.
@lifeCycleObserver('datasource')
export class EbankDbDataSource extends juggler.DataSource
  implements LifeCycleObserver {
  static dataSourceName = 'EbankDB';
  static readonly defaultConfig = config;

  constructor(
    @inject('datasources.config.EbankDB', {optional: true})
    dsConfig: object = config,
  ) {
    super(dsConfig);
  }
}
