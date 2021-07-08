import { IEmployee } from 'app/entities/employee/employee.model';

export interface IManager {
  id?: number;
  managerId?: number | null;
  managerName?: string;
  level?: string;
  employees?: IEmployee[] | null;
}

export class Manager implements IManager {
  constructor(
    public id?: number,
    public managerId?: number | null,
    public managerName?: string,
    public level?: string,
    public employees?: IEmployee[] | null
  ) {}
}

export function getManagerIdentifier(manager: IManager): number | undefined {
  return manager.id;
}
