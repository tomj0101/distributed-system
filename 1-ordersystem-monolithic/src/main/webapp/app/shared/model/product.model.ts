import dayjs from 'dayjs';
import { Condition } from 'app/shared/model/enumerations/condition.model';

export interface IProduct {
  id?: number;
  name?: string;
  description?: string | null;
  productImagesContentType?: string | null;
  productImages?: string | null;
  price?: number | null;
  condition?: Condition | null;
  active?: boolean | null;
  registerDate?: string | null;
}

export const defaultValue: Readonly<IProduct> = {
  active: false,
};
