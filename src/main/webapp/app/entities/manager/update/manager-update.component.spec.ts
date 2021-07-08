jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ManagerService } from '../service/manager.service';
import { IManager, Manager } from '../manager.model';

import { ManagerUpdateComponent } from './manager-update.component';

describe('Component Tests', () => {
  describe('Manager Management Update Component', () => {
    let comp: ManagerUpdateComponent;
    let fixture: ComponentFixture<ManagerUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let managerService: ManagerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ManagerUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ManagerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ManagerUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      managerService = TestBed.inject(ManagerService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const manager: IManager = { id: 456 };

        activatedRoute.data = of({ manager });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(manager));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Manager>>();
        const manager = { id: 123 };
        jest.spyOn(managerService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ manager });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: manager }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(managerService.update).toHaveBeenCalledWith(manager);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Manager>>();
        const manager = new Manager();
        jest.spyOn(managerService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ manager });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: manager }));
        saveSubject.complete();

        // THEN
        expect(managerService.create).toHaveBeenCalledWith(manager);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Manager>>();
        const manager = { id: 123 };
        jest.spyOn(managerService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ manager });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(managerService.update).toHaveBeenCalledWith(manager);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
