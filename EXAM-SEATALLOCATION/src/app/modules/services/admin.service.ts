import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import BASE_URL from '../../auth/services/helper';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  constructor(private http: HttpClient) {}

  public addStudent(data: any): Observable<any> {
    return this.http.post(BASE_URL + '/student/add-student', data);
  }

  public addBlock(data: any): Observable<any> {
    return this.http.post(BASE_URL + '/block/add-block', data);
  }

  public addCourses(data: any): Observable<any> {
    return this.http.post(BASE_URL + '/course/add-course', data);
  }

  public getCourses(): Observable<any> {
    return this.http.get(BASE_URL + '/course/get-courses');
  }

  // public deleteCourses(courseId:any): Observable<any> {
  //   return this.http.delete(BASE_URL + "/course/delete-course",courseId);
  // }

  public deleteCourses(courseId: number): Observable<any> {
    const url = `${BASE_URL}/course/delete-course?courseId=${courseId}`;
    return this.http.delete(url, { responseType: 'text' });
  }

  // ----------------- ALL BLOCKS SERVICES -------------------

  public getAllBlocks(): Observable<any> {
    return this.http.get(BASE_URL + '/block/get-blocks');
  }

  public getStudentInfo(student: any): Observable<any> {
    return this.http.post(
      BASE_URL + '/seat-allocation/get-student-by-block-seat-number',
      student
    );
  }

  public addstudentregistration(student: any): Observable<any> {
    return this.http.post(
      BASE_URL + '/seat-allocation/get-student-hall-ticket',
      student
    );
  }
  private studentData: any;

  setStudentData(data: any): void {
    this.studentData = data;
  }

  // Get student data (for Hall Ticket page)
  getStudentData(): any {
    return this.studentData;
  }

  addSupervisor(data: any): Observable<any> {
    return this.http.post<any>(BASE_URL + '/supervisor/add-supervisor', data); // Update with your backend endpoint
  }

  getStudentsByCourseId(courseId: number): Observable<any[]> {
    return this.http.get<any[]>(
      `${BASE_URL}/student/get-students-by-course/${courseId}`
    );
  }

  getStudentsByCourse(courseId: number, year: string): Observable<any[]> {
    const payload = { courseId, year };
    return this.http.post<any[]>(
      `${BASE_URL}/student/get-students-by-course`,
      payload
    );
  }

  getAllStudents(): Observable<any> {
    return this.http.get(BASE_URL + '/student/get-all-students');
  }

  // deleteSupervisor(supervisorId: number): Observable<any> {
  //   return this.http.delete<any>(
  //     `${BASE_URL}/supervisor/delete-supervisor?supervisorId=${supervisorId}`
  //   );
  // }

  // updateSupervisor(supervisor: any): Observable<any> {
  //   return this.http.put<any>(
  //     `${BASE_URL}/supervisor/update-supervisor`,
  //     supervisor
  //   );
  // }

  // getSupervisors(): Observable<any[]> {
  //   return this.http.get<any[]>(`${BASE_URL}/supervisor/get-supervisors`);
  // }

  getSupervisors(): Observable<any> {
    return this.http.get(`${BASE_URL}/supervisor/get-supervisors`);
  }

  addSupervisors(supervisors: any[]): Observable<any> {
    return this.http.post(`${BASE_URL}/supervisor/add-supervisor`, supervisors);
  }

  updateSupervisor(supervisor: any): Observable<any> {
    return this.http.put<any>(
      `${BASE_URL}/supervisor/update-supervisor`,
      supervisor
    );
  }

  deleteSupervisor(supervisorId: number): Observable<any> {
    // Ensure correct query parameter format for deletion
    const url = `${BASE_URL}/supervisor/delete-supervisor?supervisorId=${supervisorId}`;
    return this.http.delete<any>(url);
  }

  public getAvailableBlocks(): Observable<any> {
    return this.http.get(BASE_URL + '/block/get-available-blocks'); // Update with your backend endpoint
  }

  // Admin
  login(email: string, password: string): Observable<any> {
    return this.http.get(`${BASE_URL}/user/login-user`, {
      params: { email: email, password: password },
    });
  }

  // Get all blocks
  public getBlocks(): Observable<any> {
    return this.http.get(BASE_URL + '/block/get-blocks');
  }

  updateBlock(block: any): Observable<any> {
    return this.http.put<any>(`${BASE_URL}/block/update-block`, block);
  }

  getBlockBySeatNumberBlockNumber(blockDto: any): Observable<any> {
    return this.http.post<any>(
      `${BASE_URL}/block/get-block-by-seat-number-and-block`,
      blockDto
    );
  }

  // Delete a block by ID
  public deleteBlock(blockId: number): Observable<any> {
    const url = `${BASE_URL}/block/delete-block?blockId=${blockId}`;
    return this.http.delete(url, { responseType: 'text' });
  }

  updateCourse(course: any): Observable<any> {
    return this.http.put(`${BASE_URL}/course/update-course`, course);
  }

  getBlckBySeatNumber(seatNumber: string): Observable<any> {
    return this.http.get(
      `${BASE_URL}/seat-allocation/get-block-by-seat-number?seatNumber=${seatNumber}`
    );
  }

  getStudentPosition(positionDto: any): Observable<any> {
    return this.http.post(
      `${BASE_URL}/seat-allocation/get-student-position`,
      positionDto
    );
  }
}
