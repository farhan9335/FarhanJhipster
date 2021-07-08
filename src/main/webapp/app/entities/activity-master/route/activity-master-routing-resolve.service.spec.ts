jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IActivityMaster, ActivityMaster } from '../activity-master.model';
import { ActivityMasterService } from '../service/activity-master.service';

import { ActivityMasterRoutingResolveService } from './activity-master-routing-resolve.service';

describe('Service Tests', () => {
  describe('ActivityMaster routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ActivityMasterRoutingResolveService;
    let service: ActivityMasterService;
    let resultActivityMaster: IActivityMaster | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ActivityMasterRoutingResolveService);
      service = TestBed.inject(ActivityMasterService);
      resultActivityMaster = undefined;
    });

    describe('resolve', () => {
      it('should return IActivityMaster returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultActivityMaster = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultActivityMaster).toEqual({ id: 123 });
      });

      it('should return new IActivityMaster if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultActivityMaster = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultActivityMaster).toEqual(new ActivityMaster());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ActivityMaster })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultActivityMaster = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultActivityMaster).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
