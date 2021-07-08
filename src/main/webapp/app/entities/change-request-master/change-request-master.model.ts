export interface IChangeRequestMaster {
  id?: number;
  changeRequestId?: number | null;
  changeRequestCode?: string;
  projectActivityCode?: string;
}

export class ChangeRequestMaster implements IChangeRequestMaster {
  constructor(
    public id?: number,
    public changeRequestId?: number | null,
    public changeRequestCode?: string,
    public projectActivityCode?: string
  ) {}
}

export function getChangeRequestMasterIdentifier(changeRequestMaster: IChangeRequestMaster): number | undefined {
  return changeRequestMaster.id;
}
