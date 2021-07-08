export interface IProject {
  id?: number;
  projectId?: number | null;
  projectName?: string;
}

export class Project implements IProject {
  constructor(public id?: number, public projectId?: number | null, public projectName?: string) {}
}

export function getProjectIdentifier(project: IProject): number | undefined {
  return project.id;
}
