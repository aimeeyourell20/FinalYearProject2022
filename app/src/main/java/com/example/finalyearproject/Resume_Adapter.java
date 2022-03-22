package com.example.finalyearproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Resume_Adapter extends RecyclerView.Adapter<Resume_Adapter.ResumeViewHolder> {

    Context context;
    ArrayList<Resume_Model> resume_models;
    private FirebaseAuth mAuth;

    public Resume_Adapter (List<Resume_Model> resume_models)
    {
        this.resume_models = (ArrayList<Resume_Model>) resume_models;
    }

    public Resume_Adapter(Context c, ArrayList<Resume_Model> g){
        context = c;
        resume_models = g;
    }

    @NonNull
    @Override
    public Resume_Adapter.ResumeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.resume_details, parent, false);
        mAuth = FirebaseAuth.getInstance();

        return new Resume_Adapter.ResumeViewHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull Resume_Adapter.ResumeViewHolder holder, int position) {


        Resume_Model resume_model = resume_models.get(position);

        holder.name.setText(resume_model.getName());
        holder.bio.setText(resume_model.getBio());
        holder.company.setText(resume_model.getCompany());
        holder.role.setText(resume_model.getRole());
        holder.start.setText(resume_model.getStartDate());
        holder.end.setText(resume_model.getEndDate());
        holder.description.setText(resume_model.getDescription());
        holder.college.setText(resume_model.getCollege());
        holder.course.setText(resume_model.getCourse());
        holder.year.setText(resume_model.getGraduationYear());
        holder.skills.setText(resume_model.getSkills());
        holder.hobbies.setText(resume_model.getHobbies());
        holder.projects.setText(resume_model.getProjects());
        holder.date.setText(resume_model.getDate());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Resume_Model resume_id = resume_models.get(position);
                Intent i = new Intent(context, Mentee_Edit_Resume.class);
                i.putExtra("resume_id", resume_id.getResumeid());
                context.startActivity(i);
            }
        });

        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Resume_Model resume_id = resume_models.get(position);
                Intent i = new Intent(context, Mentee_PDF.class);
                i.putExtra("resume_id", resume_id.getResumeid());
                context.startActivity(i);
            }
        });

    }






    @Override
    public int getItemCount() {
        return resume_models.size();
    }

    class ResumeViewHolder extends RecyclerView.ViewHolder{

        TextView name, bio, company, role, start, end, description, college, course, year, skills, hobbies, projects, grades, date;
        Button edit, save;


        public ResumeViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            bio = itemView.findViewById(R.id.bio);
            company = itemView.findViewById(R.id.companyName);
            role = itemView.findViewById(R.id.role);
            start = itemView.findViewById(R.id.startDate);
            end = itemView.findViewById(R.id.endDate);
            description = itemView.findViewById(R.id.workDescription);
            college = itemView.findViewById(R.id.college);
            course = itemView.findViewById(R.id.course);
            year = itemView.findViewById(R.id.graduationYear);
            skills = itemView.findViewById(R.id.interestSkills);
            hobbies = itemView.findViewById(R.id.interestHobbies);
            projects = itemView.findViewById(R.id.interestProjects);
            grades = itemView.findViewById(R.id.collegeGrades);
            date = itemView.findViewById(R.id.date);
            edit = itemView.findViewById(R.id.editCV);
            save = itemView.findViewById(R.id.pdfCV);

        }
    }
}
