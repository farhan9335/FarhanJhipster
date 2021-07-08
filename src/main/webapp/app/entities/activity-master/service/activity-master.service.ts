import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IActivityMaster, getActivityMasterIdentifier } from '../activity-master.model';

export type EntityResponseType = HttpResponse<IActivityMaster>;
export type EntityArrayResponseType = HttpResponse<IActivityMaster[]>;

@Injectable({ providedIn: 'root' })
export class ActivityMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/activity-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(activityMaster: IActivityMaster): Observable<EntityResponseType> {
    return this.http.post<IActivityMaster>(this.resourceUrl, activityMaster, { observe: 'response' });
  }

  update(activityMaster: IActivityMaster): Observable<EntityResponseType> {
    return this.http.put<IActivityMaster>(`${this.resourceUrl}/${getActivityMasterIdentifier(activityMaster) as number}`, activityMaster, {
      observe: 'response',
    });
  }

  partialUpdate(activityMaster: IActivityMaster): Observable<EntityResponseType> {
    return this.http.patch<IActivityMaster>(
      `${this.resourceUrl}/${getActivityMasterIdentifier(activityMaster) as number}`,
      activityMaster,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IActivityMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IActivityMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addActivityMasterToCollectionIfMissing(
    activityMasterCollection: IActivityMaster[],
    ...activityMastersToCheck: (IActivityMaster | null | undefined)[]
  ): IActivityMaster[] {
    const activityMasters: IActivityMaster[] = activityMastersToCheck.filter(isPresent);
    if (activityMasters.length > 0) {
      const activityMasterCollectionIdentifiers = activityMasterCollection.map(
        activityMasterItem => getActivityMasterIdentifier(activityMasterItem)!
      );
      const activityMastersToAdd = activityMasters.filter(activityMasterItem => {
        const activityMasterIdentifier = getActivityMasterIdentifier(activityMasterItem);
        if (activityMasterIdentifier == null || activityMasterCollectionIdentifiers.includes(activityMasterIdentifier)) {
          return false;
        }
        activityMasterCollectionIdentifiers.push(activityMasterIdentifier);
        return true;
      });
      return [...activityMastersToAdd, ...activityMasterCollection];
    }
    return activityMasterCollection;
  }
}
