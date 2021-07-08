import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IChangeRequestMaster, getChangeRequestMasterIdentifier } from '../change-request-master.model';

export type EntityResponseType = HttpResponse<IChangeRequestMaster>;
export type EntityArrayResponseType = HttpResponse<IChangeRequestMaster[]>;

@Injectable({ providedIn: 'root' })
export class ChangeRequestMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/change-request-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(changeRequestMaster: IChangeRequestMaster): Observable<EntityResponseType> {
    return this.http.post<IChangeRequestMaster>(this.resourceUrl, changeRequestMaster, { observe: 'response' });
  }

  update(changeRequestMaster: IChangeRequestMaster): Observable<EntityResponseType> {
    return this.http.put<IChangeRequestMaster>(
      `${this.resourceUrl}/${getChangeRequestMasterIdentifier(changeRequestMaster) as number}`,
      changeRequestMaster,
      { observe: 'response' }
    );
  }

  partialUpdate(changeRequestMaster: IChangeRequestMaster): Observable<EntityResponseType> {
    return this.http.patch<IChangeRequestMaster>(
      `${this.resourceUrl}/${getChangeRequestMasterIdentifier(changeRequestMaster) as number}`,
      changeRequestMaster,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IChangeRequestMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IChangeRequestMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addChangeRequestMasterToCollectionIfMissing(
    changeRequestMasterCollection: IChangeRequestMaster[],
    ...changeRequestMastersToCheck: (IChangeRequestMaster | null | undefined)[]
  ): IChangeRequestMaster[] {
    const changeRequestMasters: IChangeRequestMaster[] = changeRequestMastersToCheck.filter(isPresent);
    if (changeRequestMasters.length > 0) {
      const changeRequestMasterCollectionIdentifiers = changeRequestMasterCollection.map(
        changeRequestMasterItem => getChangeRequestMasterIdentifier(changeRequestMasterItem)!
      );
      const changeRequestMastersToAdd = changeRequestMasters.filter(changeRequestMasterItem => {
        const changeRequestMasterIdentifier = getChangeRequestMasterIdentifier(changeRequestMasterItem);
        if (changeRequestMasterIdentifier == null || changeRequestMasterCollectionIdentifiers.includes(changeRequestMasterIdentifier)) {
          return false;
        }
        changeRequestMasterCollectionIdentifiers.push(changeRequestMasterIdentifier);
        return true;
      });
      return [...changeRequestMastersToAdd, ...changeRequestMasterCollection];
    }
    return changeRequestMasterCollection;
  }
}
