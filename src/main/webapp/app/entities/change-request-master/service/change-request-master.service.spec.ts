import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IChangeRequestMaster, ChangeRequestMaster } from '../change-request-master.model';

import { ChangeRequestMasterService } from './change-request-master.service';

describe('Service Tests', () => {
  describe('ChangeRequestMaster Service', () => {
    let service: ChangeRequestMasterService;
    let httpMock: HttpTestingController;
    let elemDefault: IChangeRequestMaster;
    let expectedResult: IChangeRequestMaster | IChangeRequestMaster[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ChangeRequestMasterService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        changeRequestId: 0,
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

      it('should create a ChangeRequestMaster', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ChangeRequestMaster()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ChangeRequestMaster', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            changeRequestId: 1,
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

      it('should partial update a ChangeRequestMaster', () => {
        const patchObject = Object.assign({}, new ChangeRequestMaster());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ChangeRequestMaster', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            changeRequestId: 1,
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

      it('should delete a ChangeRequestMaster', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addChangeRequestMasterToCollectionIfMissing', () => {
        it('should add a ChangeRequestMaster to an empty array', () => {
          const changeRequestMaster: IChangeRequestMaster = { id: 123 };
          expectedResult = service.addChangeRequestMasterToCollectionIfMissing([], changeRequestMaster);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(changeRequestMaster);
        });

        it('should not add a ChangeRequestMaster to an array that contains it', () => {
          const changeRequestMaster: IChangeRequestMaster = { id: 123 };
          const changeRequestMasterCollection: IChangeRequestMaster[] = [
            {
              ...changeRequestMaster,
            },
            { id: 456 },
          ];
          expectedResult = service.addChangeRequestMasterToCollectionIfMissing(changeRequestMasterCollection, changeRequestMaster);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ChangeRequestMaster to an array that doesn't contain it", () => {
          const changeRequestMaster: IChangeRequestMaster = { id: 123 };
          const changeRequestMasterCollection: IChangeRequestMaster[] = [{ id: 456 }];
          expectedResult = service.addChangeRequestMasterToCollectionIfMissing(changeRequestMasterCollection, changeRequestMaster);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(changeRequestMaster);
        });

        it('should add only unique ChangeRequestMaster to an array', () => {
          const changeRequestMasterArray: IChangeRequestMaster[] = [{ id: 123 }, { id: 456 }, { id: 94082 }];
          const changeRequestMasterCollection: IChangeRequestMaster[] = [{ id: 123 }];
          expectedResult = service.addChangeRequestMasterToCollectionIfMissing(changeRequestMasterCollection, ...changeRequestMasterArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const changeRequestMaster: IChangeRequestMaster = { id: 123 };
          const changeRequestMaster2: IChangeRequestMaster = { id: 456 };
          expectedResult = service.addChangeRequestMasterToCollectionIfMissing([], changeRequestMaster, changeRequestMaster2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(changeRequestMaster);
          expect(expectedResult).toContain(changeRequestMaster2);
        });

        it('should accept null and undefined values', () => {
          const changeRequestMaster: IChangeRequestMaster = { id: 123 };
          expectedResult = service.addChangeRequestMasterToCollectionIfMissing([], null, changeRequestMaster, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(changeRequestMaster);
        });

        it('should return initial array if no ChangeRequestMaster is added', () => {
          const changeRequestMasterCollection: IChangeRequestMaster[] = [{ id: 123 }];
          expectedResult = service.addChangeRequestMasterToCollectionIfMissing(changeRequestMasterCollection, undefined, null);
          expect(expectedResult).toEqual(changeRequestMasterCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
