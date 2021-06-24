import {
  Count,
  CountSchema,
  Filter,
  FilterExcludingWhere,
  repository,
  Where,
} from '@loopback/repository';
import {
  post,
  param,
  get,
  getModelSchemaRef,
  patch,
  put,
  del,
  requestBody,
  response,
} from '@loopback/rest';
import {Status} from '../models';
import {StatusRepository} from '../repositories';

export class StatusController {
  constructor(
    @repository(StatusRepository)
    public statusRepository : StatusRepository,
  ) {}

  @post('/status')
  @response(200, {
    description: 'Status model instance',
    content: {'application/json': {schema: getModelSchemaRef(Status)}},
  })
  async create(
    @requestBody({
      content: {
        'application/json': {
          schema: getModelSchemaRef(Status, {
            title: 'NewStatus',
            exclude: ['id'],
          }),
        },
      },
    })
    status: Omit<Status, 'id'>,
  ): Promise<Status> {
    return this.statusRepository.create(status);
  }

  @get('/status/count')
  @response(200, {
    description: 'Status model count',
    content: {'application/json': {schema: CountSchema}},
  })
  async count(
    @param.where(Status) where?: Where<Status>,
  ): Promise<Count> {
    return this.statusRepository.count(where);
  }

  @get('/status')
  @response(200, {
    description: 'Array of Status model instances',
    content: {
      'application/json': {
        schema: {
          type: 'array',
          items: getModelSchemaRef(Status, {includeRelations: true}),
        },
      },
    },
  })
  async find(
    @param.filter(Status) filter?: Filter<Status>,
  ): Promise<Status[]> {
    return this.statusRepository.find(filter);
  }

  @patch('/status')
  @response(200, {
    description: 'Status PATCH success count',
    content: {'application/json': {schema: CountSchema}},
  })
  async updateAll(
    @requestBody({
      content: {
        'application/json': {
          schema: getModelSchemaRef(Status, {partial: true}),
        },
      },
    })
    status: Status,
    @param.where(Status) where?: Where<Status>,
  ): Promise<Count> {
    return this.statusRepository.updateAll(status, where);
  }

  @get('/status/{id}')
  @response(200, {
    description: 'Status model instance',
    content: {
      'application/json': {
        schema: getModelSchemaRef(Status, {includeRelations: true}),
      },
    },
  })
  async findById(
    @param.path.string('id') id: string,
    @param.filter(Status, {exclude: 'where'}) filter?: FilterExcludingWhere<Status>
  ): Promise<Status> {
    return this.statusRepository.findById(id, filter);
  }

  @patch('/status/{id}')
  @response(204, {
    description: 'Status PATCH success',
  })
  async updateById(
    @param.path.string('id') id: string,
    @requestBody({
      content: {
        'application/json': {
          schema: getModelSchemaRef(Status, {partial: true}),
        },
      },
    })
    status: Status,
  ): Promise<void> {
    await this.statusRepository.updateById(id, status);
  }

  @put('/status/{id}')
  @response(204, {
    description: 'Status PUT success',
  })
  async replaceById(
    @param.path.string('id') id: string,
    @requestBody() status: Status,
  ): Promise<void> {
    await this.statusRepository.replaceById(id, status);
  }

  @del('/status/{id}')
  @response(204, {
    description: 'Status DELETE success',
  })
  async deleteById(@param.path.string('id') id: string): Promise<void> {
    await this.statusRepository.deleteById(id);
  }
}
