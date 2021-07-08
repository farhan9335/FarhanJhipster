import { IManager } from 'app/entities/manager/manager.model';

export interface IEmployee {
  id?: number;
  employeeId?: number | null;
  employeeName?: string;
  employeeSpecialization?: string | null;
  manager?: IManager | null;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public employeeId?: number | null,
    public employeeName?: string,
    public employeeSpecialization?: string | null,
    public manager?: IManager | null
  ) {}
}

export function getEmployeeIdentifier(employee: IEmployee): number | undefined {
  return employee.id;
}
