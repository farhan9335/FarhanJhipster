jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EmployeeService } from '../service/employee.service';
import { IEmployee, Employee } from '../employee.model';
import { IManager } from 'app/entities/manager/manager.model';
import { ManagerService } from 'app/entities/manager/service/manager.service';

import { EmployeeUpdateComponent } from './employee-update.component';

describe('Component Tests', () => {
  describe('Employee Management Update Component', () => {
    let comp: EmployeeUpdateComponent;
    let fixture: ComponentFixture<EmployeeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let employeeService: EmployeeService;
    let managerService: ManagerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EmployeeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EmployeeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmployeeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      employeeService = TestBed.inject(EmployeeService);
      managerService = TestBed.inject(ManagerService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Manager query and add missing value', () => {
        const employee: IEmployee = { id: 456 };
        const manager: IManager = { id: 26940 };
        employee.manager = manager;

        const managerCollection: IManager[] = [{ id: 35893 }];
        jest.spyOn(managerService, 'query').mockReturnValue(of(new HttpResponse({ body: managerCollection })));
        const additionalManagers = [manager];
        const expectedCollection: IManager[] = [...additionalManagers, ...managerCollection];
        jest.spyOn(managerService, 'addManagerToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ employee });
        comp.ngOnInit();

        expect(managerService.query).toHaveBeenCalled();
        expect(managerService.addManagerToCollectionIfMissing).toHaveBeenCalledWith(managerCollection, ...additionalManagers);
        expect(comp.managersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const employee: IEmployee = { id: 456 };
        const manager: IManager = { id: 4260 };
        employee.manager = manager;

        activatedRoute.data = of({ employee });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(employee));
        expect(comp.managersSharedCollection).toContain(manager);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Employee>>();
        const employee = { id: 123 };
        jest.spyOn(employeeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ employee });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: employee }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(employeeService.update).toHaveBeenCalledWith(employee);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Employee>>();
        const employee = new Employee();
        jest.spyOn(employeeService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ employee });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: employee }));
        saveSubject.complete();

        // THEN
        expect(employeeService.create).toHaveBeenCalledWith(employee);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Employee>>();
        const employee = { id: 123 };
        jest.spyOn(employeeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ employee });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(employeeService.update).toHaveBeenCalledWith(employee);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackManagerById', () => {
        it('Should return tracked Manager primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackManagerById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
