import dayjs from 'dayjs';
import { IAddress } from 'app/shared/model/address.model';
import { IStatus } from 'app/shared/model/status.model';
import { IUser } from 'app/shared/model/user.model';

export interface IOrderMaster {
  id?: number;
  paymentMethod?: string | null;
  total?: number | null;
  email?: string;
  registerDate?: string | null;
  address?: IAddress | null;
  status?: IStatus | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IOrderMaster> = {};
