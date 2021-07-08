export interface IActivityMaster {
  id?: number;
  activityMasterId?: number | null;
  activity?: string;
  changeRequestCode?: string;
  projectActivityCode?: string;
}

export class ActivityMaster implements IActivityMaster {
  constructor(
    public id?: number,
    public activityMasterId?: number | null,
    public activity?: string,
    public changeRequestCode?: string,
    public projectActivityCode?: string
  ) {}
}

export function getActivityMasterIdentifier(activityMaster: IActivityMaster): number | undefined {
  return activityMaster.id;
}
