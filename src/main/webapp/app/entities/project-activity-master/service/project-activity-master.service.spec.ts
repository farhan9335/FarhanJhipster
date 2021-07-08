import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProjectActivityMaster, ProjectActivityMaster } from '../project-activity-master.model';

import { ProjectActivityMasterService } from './project-activity-master.service';

describe('Service Tests', () => {
  describe('ProjectActivityMaster Service', () => {
    let service: ProjectActivityMasterService;
    let httpMock: HttpTestingController;
    let elemDefault: IProjectActivityMaster;
    let expectedResult: IProjectActivityMaster | IProjectActivityMaster[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ProjectActivityMasterService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        projectActivityId: 0,
        projectActivityCode: 'AAAAAAA',
        description: 'AAAAAAA',
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

      it('should create a ProjectActivityMaster', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ProjectActivityMaster()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ProjectActivityMaster', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            projectActivityId: 1,
            projectActivityCode: 'BBBBBB',
            description: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a ProjectActivityMaster', () => {
        const patchObject = Object.assign(
          {
            projectActivityId: 1,
            projectActivityCode: 'BBBBBB',
          },
          new ProjectActivityMaster()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ProjectActivityMaster', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            projectActivityId: 1,
            projectActivityCode: 'BBBBBB',
            description: 'BBBBBB',
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

      it('should delete a ProjectActivityMaster', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addProjectActivityMasterToCollectionIfMissing', () => {
        it('should add a ProjectActivityMaster to an empty array', () => {
          const projectActivityMaster: IProjectActivityMaster = { id: 123 };
          expectedResult = service.addProjectActivityMasterToCollectionIfMissing([], projectActivityMaster);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(projectActivityMaster);
        });

        it('should not add a ProjectActivityMaster to an array that contains it', () => {
          const projectActivityMaster: IProjectActivityMaster = { id: 123 };
          const projectActivityMasterCollection: IProjectActivityMaster[] = [
            {
              ...projectActivityMaster,
            },
            { id: 456 },
          ];
          expectedResult = service.addProjectActivityMasterToCollectionIfMissing(projectActivityMasterCollection, projectActivityMaster);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ProjectActivityMaster to an array that doesn't contain it", () => {
          const projectActivityMaster: IProjectActivityMaster = { id: 123 };
          const projectActivityMasterCollection: IProjectActivityMaster[] = [{ id: 456 }];
          expectedResult = service.addProjectActivityMasterToCollectionIfMissing(projectActivityMasterCollection, projectActivityMaster);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(projectActivityMaster);
        });

        it('should add only unique ProjectActivityMaster to an array', () => {
          const projectActivityMasterArray: IProjectActivityMaster[] = [{ id: 123 }, { id: 456 }, { id: 4852 }];
          const projectActivityMasterCollection: IProjectActivityMaster[] = [{ id: 123 }];
          expectedResult = service.addProjectActivityMasterToCollectionIfMissing(
            projectActivityMasterCollection,
            ...projectActivityMasterArray
          );
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const projectActivityMaster: IProjectActivityMaster = { id: 123 };
          const projectActivityMaster2: IProjectActivityMaster = { id: 456 };
          expectedResult = service.addProjectActivityMasterToCollectionIfMissing([], projectActivityMaster, projectActivityMaster2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(projectActivityMaster);
          expect(expectedResult).toContain(projectActivityMaster2);
        });

        it('should accept null and undefined values', () => {
          const projectActivityMaster: IProjectActivityMaster = { id: 123 };
          expectedResult = service.addProjectActivityMasterToCollectionIfMissing([], null, projectActivityMaster, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(projectActivityMaster);
        });

        it('should return initial array if no ProjectActivityMaster is added', () => {
          const projectActivityMasterCollection: IProjectActivityMaster[] = [{ id: 123 }];
          expectedResult = service.addProjectActivityMasterToCollectionIfMissing(projectActivityMasterCollection, undefined, null);
          expect(expectedResult).toEqual(projectActivityMasterCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
