import dayjs from 'dayjs';
import { IIssueSystem } from 'app/shared/model/issue-system.model';
import { SupportSeverity } from 'app/shared/model/enumerations/support-severity.model';
import { SupportStatus } from 'app/shared/model/enumerations/support-status.model';

export interface ICustomerSupport {
  id?: number;
  description?: string;
  cCreated?: string;
  severity?: SupportSeverity | null;
  status?: SupportStatus | null;
  issueSystem?: IIssueSystem | null;
}

export const defaultValue: Readonly<ICustomerSupport> = {};
