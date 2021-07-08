import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IActivityMaster, ActivityMaster } from '../activity-master.model';

import { ActivityMasterService } from './activity-master.service';

describe('Service Tests', () => {
  describe('ActivityMaster Service', () => {
    let service: ActivityMasterService;
    let httpMock: HttpTestingController;
    let elemDefault: IActivityMaster;
    let expectedResult: IActivityMaster | IActivityMaster[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ActivityMasterService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        activityMasterId: 0,
        activity: 'AAAAAAA',
        changeRequestCode: 'AAAAAAA',
        projectActivityCode: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ActivityMaster', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ActivityMaster()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ActivityMaster', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            activityMasterId: 1,
            activity: 'BBBBBB',
            changeRequestCode: 'BBBBBB',
            projectActivityCode: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a ActivityMaster', () => {
        const patchObject = Object.assign(
          {
            activity: 'BBBBBB',
          },
          new ActivityMaster()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ActivityMaster', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            activityMasterId: 1,
            activity: 'BBBBBB',
            changeRequestCode: 'BBBBBB',
            projectActivityCode: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ActivityMaster', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addActivityMasterToCollectionIfMissing', () => {
        it('should add a ActivityMaster to an empty array', () => {
          const activityMaster: IActivityMaster = { id: 123 };
          expectedResult = service.addActivityMasterToCollectionIfMissing([], activityMaster);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(activityMaster);
        });

        it('should not add a ActivityMaster to an array that contains it', () => {
          const activityMaster: IActivityMaster = { id: 123 };
          const activityMasterCollection: IActivityMaster[] = [
            {
              ...activityMaster,
            },
            { id: 456 },
          ];
          expectedResult = service.addActivityMasterToCollectionIfMissing(activityMasterCollection, activityMaster);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ActivityMaster to an array that doesn't contain it", () => {
          const activityMaster: IActivityMaster = { id: 123 };
          const activityMasterCollection: IActivityMaster[] = [{ id: 456 }];
          expectedResult = service.addActivityMasterToCollectionIfMissing(activityMasterCollection, activityMaster);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(activityMaster);
        });

        it('should add only unique ActivityMaster to an array', () => {
          const activityMasterArray: IActivityMaster[] = [{ id: 123 }, { id: 456 }, { id: 86568 }];
          const activityMasterCollection: IActivityMaster[] = [{ id: 123 }];
          expectedResult = service.addActivityMasterToCollectionIfMissing(activityMasterCollection, ...activityMasterArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const activityMaster: IActivityMaster = { id: 123 };
          const activityMaster2: IActivityMaster = { id: 456 };
          expectedResult = service.addActivityMasterToCollectionIfMissing([], activityMaster, activityMaster2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(activityMaster);
          expect(expectedResult).toContain(activityMaster2);
        });

        it('should accept null and undefined values', () => {
          const activityMaster: IActivityMaster = { id: 123 };
          expectedResult = service.addActivityMasterToCollectionIfMissing([], null, activityMaster, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(activityMaster);
        });

        it('should return initial array if no ActivityMaster is added', () => {
          const activityMasterCollection: IActivityMaster[] = [{ id: 123 }];
          expectedResult = service.addActivityMasterToCollectionIfMissing(activityMasterCollection, undefined, null);
          expect(expectedResult).toEqual(activityMasterCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
