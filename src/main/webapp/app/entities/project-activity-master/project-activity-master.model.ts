export interface IProjectActivityMaster {
  id?: number;
  projectActivityId?: number | null;
  projectActivityCode?: string;
  description?: string;
}

export class ProjectActivityMaster implements IProjectActivityMaster {
  constructor(
    public id?: number,
    public projectActivityId?: number | null,
    public projectActivityCode?: string,
    public description?: string
  ) {}
}

export function getProjectActivityMasterIdentifier(projectActivityMaster: IProjectActivityMaster): number | undefined {
  return projectActivityMaster.id;
}
