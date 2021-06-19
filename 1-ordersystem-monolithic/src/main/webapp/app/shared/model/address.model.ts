import { IUser } from 'app/shared/model/user.model';

export interface IAddress {
  id?: number;
  streetAddress?: string;
  postalCode?: number;
  city?: string;
  stateProvince?: string;
  user?: IUser | null;
}

export const defaultValue: Readonly<IAddress> = {};
