jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IChangeRequestMaster, ChangeRequestMaster } from '../change-request-master.model';
import { ChangeRequestMasterService } from '../service/change-request-master.service';

import { ChangeRequestMasterRoutingResolveService } from './change-request-master-routing-resolve.service';

describe('Service Tests', () => {
  describe('ChangeRequestMaster routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ChangeRequestMasterRoutingResolveService;
    let service: ChangeRequestMasterService;
    let resultChangeRequestMaster: IChangeRequestMaster | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ChangeRequestMasterRoutingResolveService);
      service = TestBed.inject(ChangeRequestMasterService);
      resultChangeRequestMaster = undefined;
    });

    describe('resolve', () => {
      it('should return IChangeRequestMaster returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultChangeRequestMaster = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultChangeRequestMaster).toEqual({ id: 123 });
      });

      it('should return new IChangeRequestMaster if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultChangeRequestMaster = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultChangeRequestMaster).toEqual(new ChangeRequestMaster());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ChangeRequestMaster })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultChangeRequestMaster = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultChangeRequestMaster).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
