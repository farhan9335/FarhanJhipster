import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProjectActivityMaster, getProjectActivityMasterIdentifier } from '../project-activity-master.model';

export type EntityResponseType = HttpResponse<IProjectActivityMaster>;
export type EntityArrayResponseType = HttpResponse<IProjectActivityMaster[]>;

@Injectable({ providedIn: 'root' })
export class ProjectActivityMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/project-activity-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(projectActivityMaster: IProjectActivityMaster): Observable<EntityResponseType> {
    return this.http.post<IProjectActivityMaster>(this.resourceUrl, projectActivityMaster, { observe: 'response' });
  }

  update(projectActivityMaster: IProjectActivityMaster): Observable<EntityResponseType> {
    return this.http.put<IProjectActivityMaster>(
      `${this.resourceUrl}/${getProjectActivityMasterIdentifier(projectActivityMaster) as number}`,
      projectActivityMaster,
      { observe: 'response' }
    );
  }

  partialUpdate(projectActivityMaster: IProjectActivityMaster): Observable<EntityResponseType> {
    return this.http.patch<IProjectActivityMaster>(
      `${this.resourceUrl}/${getProjectActivityMasterIdentifier(projectActivityMaster) as number}`,
      projectActivityMaster,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectActivityMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectActivityMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addProjectActivityMasterToCollectionIfMissing(
    projectActivityMasterCollection: IProjectActivityMaster[],
    ...projectActivityMastersToCheck: (IProjectActivityMaster | null | undefined)[]
  ): IProjectActivityMaster[] {
    const projectActivityMasters: IProjectActivityMaster[] = projectActivityMastersToCheck.filter(isPresent);
    if (projectActivityMasters.length > 0) {
      const projectActivityMasterCollectionIdentifiers = projectActivityMasterCollection.map(
        projectActivityMasterItem => getProjectActivityMasterIdentifier(projectActivityMasterItem)!
      );
      const projectActivityMastersToAdd = projectActivityMasters.filter(projectActivityMasterItem => {
        const projectActivityMasterIdentifier = getProjectActivityMasterIdentifier(projectActivityMasterItem);
        if (
          projectActivityMasterIdentifier == null ||
          projectActivityMasterCollectionIdentifiers.includes(projectActivityMasterIdentifier)
        ) {
          return false;
        }
        projectActivityMasterCollectionIdentifiers.push(projectActivityMasterIdentifier);
        return true;
      });
      return [...projectActivityMastersToAdd, ...projectActivityMasterCollection];
    }
    return projectActivityMasterCollection;
  }
}
