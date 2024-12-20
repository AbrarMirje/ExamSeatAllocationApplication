import { NgModule } from '@angular/core';
import {
  BrowserModule,
  provideClientHydration,
} from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { StudentFormComponent } from './modules/components/student-form/student-form.component';
import { SidebarComponent } from './modules/components/sidebar/sidebar.component';
import { NavbarComponent } from './modules/components/navbar/navbar.component';
import { FooterComponent } from './modules/components/footer/footer.component';
import { DashboardComponent } from './modules/components/dashboard/dashboard.component';
import { HomeComponent } from './modules/components/home/home.component';
import { AddBlockComponent } from './modules/components/add-block/add-block.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ToastrModule } from 'ngx-toastr';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AddCoursesComponent } from './modules/components/add-courses/add-courses.component';
import { GoogleFormComponent } from './modules/components/hall-ticket-form/google-form.component';
import { HallTicketComponent } from './modules/components/hall-ticket/hall-ticket.component';
import { AddSupervisorComponent } from './modules/components/add-supervisor/add-supervisor.component';
import { ShowStudentinfoComponent } from './modules/components/show-studentinfo/show-studentinfo.component';
import { AdminComponent } from './modules/components/admin/admin.component';
import { AdminHomeComponent } from './modules/components/admin-home/admin-home.component';

@NgModule({
  declarations: [
    AppComponent,
    StudentFormComponent,
    SidebarComponent,
    NavbarComponent,
    FooterComponent,
    DashboardComponent,
    HomeComponent,
    AddBlockComponent,
    AddCoursesComponent,
    GoogleFormComponent,
    HallTicketComponent,
    AddSupervisorComponent,
    ShowStudentinfoComponent,
    AdminComponent,
    AdminHomeComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    ReactiveFormsModule,
    ToastrModule.forRoot(),
    HttpClientModule,
    FormsModule,
  ],
  providers: [provideClientHydration()],
  bootstrap: [AppComponent],
})
export class AppModule {}
