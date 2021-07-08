jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IProjectActivityMaster, ProjectActivityMaster } from '../project-activity-master.model';
import { ProjectActivityMasterService } from '../service/project-activity-master.service';

import { ProjectActivityMasterRoutingResolveService } from './project-activity-master-routing-resolve.service';

describe('Service Tests', () => {
  describe('ProjectActivityMaster routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ProjectActivityMasterRoutingResolveService;
    let service: ProjectActivityMasterService;
    let resultProjectActivityMaster: IProjectActivityMaster | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ProjectActivityMasterRoutingResolveService);
      service = TestBed.inject(ProjectActivityMasterService);
      resultProjectActivityMaster = undefined;
    });

    describe('resolve', () => {
      it('should return IProjectActivityMaster returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultProjectActivityMaster = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultProjectActivityMaster).toEqual({ id: 123 });
      });

      it('should return new IProjectActivityMaster if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultProjectActivityMaster = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultProjectActivityMaster).toEqual(new ProjectActivityMaster());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ProjectActivityMaster })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultProjectActivityMaster = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultProjectActivityMaster).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
