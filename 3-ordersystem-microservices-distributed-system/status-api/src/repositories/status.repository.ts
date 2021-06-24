import {inject} from '@loopback/core';
import {DefaultCrudRepository} from '@loopback/repository';
import {EbankDbDataSource} from '../datasources';
import {Status, StatusRelations} from '../models';

export class StatusRepository extends DefaultCrudRepository<
  Status,
  typeof Status.prototype.id,
  StatusRelations
> {
  constructor(
    @inject('datasources.EbankDB') dataSource: EbankDbDataSource,
  ) {
    super(Status, dataSource);
  }
}
