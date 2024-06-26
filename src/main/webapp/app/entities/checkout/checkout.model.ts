import dayjs from 'dayjs/esm';
import { IBookCopy } from 'app/entities/book-copy/book-copy.model';
import { IPatronAccount } from 'app/entities/patron-account/patron-account.model';

export interface ICheckout {
  id: number;
  startTime?: dayjs.Dayjs | null;
  endTime?: dayjs.Dayjs | null;
  isReturned?: boolean | null;
  copy?: Pick<IBookCopy, 'id'> | null;
  patron?: Pick<IPatronAccount, 'cardNumber'> | null;
}

export type NewCheckout = Omit<ICheckout, 'id'> & { id: null };
