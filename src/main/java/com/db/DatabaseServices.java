package com.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.main.Job;
import com.main.TestJob;

public class DatabaseServices {

    // Create the schema (jobs table)

    public static void createUserTable(){
        try (Connection connection = DatabaseConnection.getConnection()) {

            String createUsersTable = """
                CREATE TABLE IF NOT EXISTS users (
                    id SERIAL PRIMARY KEY,
                    username VARCHAR(50) UNIQUE NOT NULL,
                    password VARCHAR(255) NOT NULL,
                    email VARCHAR(100) UNIQUE NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    );
                    """;
                        
                    Statement statement = connection.createStatement();
                    statement.execute(createUsersTable);
                    System.out.println("User table has been created successfully");
            } catch (SQLException e) {
                System.err.println("Oops! something went wrong while creating table. Error: " + e.getMessage());
            }
        
    }

    public static void createDatabaseSchema() {
        try (Connection connection = DatabaseConnection.getConnection()) {

            String createJobsTable = """
                        CREATE TABLE IF NOT EXISTS jobs (
                            id SERIAL PRIMARY KEY,
                            job_title VARCHAR(255),
                            activity_sector VARCHAR(255),
                            job_function VARCHAR(255),
                            required_experience VARCHAR(255),
                            study_level VARCHAR(255),
                            contract_type VARCHAR(255),
                            searched_profile TEXT,
                            remote_work VARCHAR(50),
                            city VARCHAR(255),
                            job_description TEXT,
                            site_web VARCHAR(255),
                            image_url TEXT,
                            job_page_url TEXT,
                            entreprise VARCHAR(255),
                            entreprise_address TEXT,
                            publication_date VARCHAR(50),
                            entreprise_description TEXT,
                            soft_skills TEXT,
                            salary NUMERIC(10, 2),
                            region VARCHAR(255),
                            hard_skills TEXT,
                            language VARCHAR(255),
                            apply_before VARCHAR(50),
                            language_level VARCHAR(255)
                        );
                    """;

            Statement statement = connection.createStatement();
            statement.execute(createJobsTable);
            System.out.println("Table has been created successfully");

        } catch (SQLException e) {
            System.err.println("Oops! something went wrong while creating table. Error: " + e.getMessage());
        }
    }

    // Insert a job record into the jobs table
    public static void insertJob(Job job) {
        String insertIntoTableSQL = """
                    INSERT INTO jobs (
                        job_title, activity_sector, function, required_experience, study_level, contract_type,
                        searched_profile, remote_work, city, job_description, site_web, image_url, job_page_url,
                        entreprise, entreprise_address, publication_date, entreprise_description, soft_skills, salary,
                        region, hard_skills, language, apply_before, language_level
                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insertIntoTableSQL)) {

            preparedStatement.setString(1, job.getJobTitle());
            preparedStatement.setString(2, job.getActivitySector());
            preparedStatement.setString(3, job.getFunction());
            preparedStatement.setString(4, job.getRequiredExperience());
            preparedStatement.setString(5, job.getStudyLevel());
            preparedStatement.setString(6, job.getContractType());
            preparedStatement.setString(7, job.getSearchedProfile());
            preparedStatement.setString(8, job.getRemoteWork());
            preparedStatement.setString(9, job.getCity());
            preparedStatement.setString(10, job.getJobDescription());
            preparedStatement.setString(11, job.getSiteWeb());
            preparedStatement.setString(12, job.getImageUrl());
            preparedStatement.setString(13, job.getJobPageUrl());
            preparedStatement.setString(14, job.getEntreprise());
            preparedStatement.setString(15, job.getEntrepriseAddress());

            preparedStatement.setString(16, job.getPublicationDate());
            preparedStatement.setString(17, job.getEntrepriseDescription());
            preparedStatement.setString(18, job.getSoftSkills());
            preparedStatement.setDouble(19, job.getSalary());
            preparedStatement.setString(20, job.getRegion());
            preparedStatement.setString(21, job.getHardSkills());
            preparedStatement.setString(22, job.getLanguage());

            preparedStatement.setString(23, job.getApplyBefore());
            preparedStatement.setString(24, job.getLanguageLevel());

            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println("Total number of rows inserted: " + rowsInserted);
            if (rowsInserted > 0) {
                System.out.println("A new job was inserted successfully!");
            }

        } catch (SQLException e) {
            System.err.println("Oops! Something went wrong while inserting the job. Error: " + e.getMessage());
        }
    }


    public static void insertJobsList(List<Job> jobs){
        String insertIntoTableSQL = """
            INSERT INTO jobs (
                job_title, activity_sector, job_function, required_experience, study_level, contract_type,
                searched_profile, remote_work, city, job_description, site_web, image_url, job_page_url,
                entreprise, entreprise_address, publication_date, entreprise_description, soft_skills, salary,
                region, hard_skills, language, apply_before, language_level
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
        """;

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertIntoTableSQL)) {
            connection.setAutoCommit(false);
            int count = 0;
            for (Job job : jobs) {
                job.transformFieldsToLowerCase();
                preparedStatement.setString(1, job.getJobTitle());
            preparedStatement.setString(2, job.getActivitySector());
            preparedStatement.setString(3, job.getFunction());
            preparedStatement.setString(4, job.getRequiredExperience());
            preparedStatement.setString(5, job.getStudyLevel());
            preparedStatement.setString(6, job.getContractType());
            preparedStatement.setString(7, job.getSearchedProfile());
            preparedStatement.setString(8, job.getRemoteWork());
            preparedStatement.setString(9, job.getCity());
            preparedStatement.setString(10, job.getJobDescription());
            preparedStatement.setString(11, job.getSiteWeb());
            preparedStatement.setString(12, job.getImageUrl());
            preparedStatement.setString(13, job.getJobPageUrl());
            preparedStatement.setString(14, job.getEntreprise());
            preparedStatement.setString(15, job.getEntrepriseAddress());

            preparedStatement.setString(16, job.getPublicationDate());
            preparedStatement.setString(17, job.getEntrepriseDescription());
            preparedStatement.setString(18, job.getSoftSkills());
            if (job.getSalary() != null) {
                preparedStatement.setDouble(19, job.getSalary());
            } else {
                preparedStatement.setNull(19, Types.DOUBLE); // Use appropriate SQL type
            }
            preparedStatement.setString(20, job.getRegion());
            preparedStatement.setString(21, job.getHardSkills());
            preparedStatement.setString(22, job.getLanguage());

            preparedStatement.setString(23, job.getApplyBefore());
            preparedStatement.setString(24, job.getLanguageLevel());
                preparedStatement.addBatch();
                
                if (++count % 100 == 0) { 
                    preparedStatement.executeBatch();
                }
            }
            preparedStatement.executeBatch();
            connection.commit();
            System.out.println("All rows have been inserted successfully!");
        }
        catch(SQLException e){
            System.out.println("something went wrong: "+e.getMessage());
        }


    }
    // Méthodes pour récupérer les données nécessaires aux graphiques

    // Récupérer le nombre d'offres par ville
    public static Map<String, Integer> getJobsByCity() {
        String query = "SELECT city, COUNT(*) as total_jobs FROM jobs WHERE city IS NOT NULL GROUP BY city ORDER BY total_jobs DESC";
        return executeQuery(query);
    }

    // Récupérer le nombre d'offres par secteur d'activité
    public static Map<String, Integer> getJobsByActivitySector() {
        String query = "SELECT activity_sector, COUNT(*) AS total_jobs FROM jobs WHERE activity_sector IS NOT NULL GROUP BY activity_sector ORDER BY total_jobs DESC";
        return executeQuery(query);
    }

    // Récupérer le nombre d'offres par expérience requise
    public static Map<String, Integer> getJobsByExperience() {
        String query = "SELECT required_experience, COUNT(*) AS total_jobs FROM jobs WHERE required_experience IS NOT NULL GROUP BY required_experience ORDER BY total_jobs DESC";
        return executeQuery(query);
    }

    // Récupérer le nombre d'offres par niveau d'études
    public static Map<String, Integer> getJobsByStudyLevel() {
        String query = "SELECT study_level, COUNT(*) AS total_jobs FROM jobs WHERE study_level IS NOT NULL GROUP BY study_level ORDER BY total_jobs DESC";
        return executeQuery(query);
    }

    // Récupérer le nombre d'offres par type de contrat
    public static Map<String, Integer> getJobsByContractType() {
        String query = "SELECT contract_type, COUNT(*) AS total_jobs FROM jobs WHERE contract_type IS NOT NULL GROUP BY contract_type ORDER BY total_jobs DESC";
        return executeQuery(query);
    }

    // Récupérer le nombre d'offres par télétravail
    public static Map<String, Integer> getJobsByRemoteWork() {
        String query = "SELECT remote_work, COUNT(*) AS total_jobs FROM jobs WHERE remote_work IS NOT NULL GROUP BY remote_work ORDER BY total_jobs DESC";
        return executeQuery(query);
    }

    // Récupérer le nombre d'offres par metier
    public static Map<String, Integer> getFunction() {
        String query = "SELECT job_function, COUNT(*) AS total_jobs FROM jobs WHERE job_function IS NOT NULL GROUP BY job_function ORDER BY total_jobs DESC";
        return executeQuery(query);
    }

    // Récupérer les offres par expérience et type de contrat
    public static Map<String, Map<String, Integer>> getJobsByExperienceAndContractType() {
        String query = """
                    SELECT required_experience, contract_type, COUNT(*) AS total_jobs
                    FROM jobs
                    WHERE required_experience IS NOT NULL AND contract_type IS NOT NULL
                    GROUP BY required_experience, contract_type
                    HAVING COUNT(*) > 100
                    ORDER BY required_experience, total_jobs DESC;
                """;
        return executeComplexQuery(query);
    }

    // Récupérer les offres par ville et expérience requise
    public static Map<String, Map<String, Integer>> getJobsByCityAndExperience() {
        String query = """
                    SELECT city, required_experience, COUNT(*) AS total_jobs
                    FROM jobs
                    WHERE city IS NOT NULL AND required_experience IS NOT NULL
                    GROUP BY city, required_experience
                    HAVING COUNT(*) >= 60
                    ORDER BY city, total_jobs DESC;
                """;
        return executeComplexQuery(query);
    }

    // Récupérer les offres par niveau d'études et télétravail
    public static Map<String, Map<String, Integer>> getJobsByStudyLevelAndRemoteWork() {
        String query = """
                    SELECT study_level, remote_work, COUNT(*) AS total_jobs
                    FROM jobs
                    WHERE study_level IS NOT NULL AND remote_work IS NOT NULL
                    GROUP BY study_level, remote_work
                    ORDER BY study_level, total_jobs DESC;
                """;
        return executeComplexQuery(query);
    }

    // Récupérer les offres par type de contrat et télétravail
    public static Map<String, Map<String, Integer>> getJobsByContractTypeAndRemoteWork() {
        String query = """
                    SELECT contract_type, remote_work, COUNT(*) AS total_jobs
                    FROM jobs
                    WHERE contract_type IS NOT NULL AND remote_work IS NOT NULL
                    GROUP BY contract_type, remote_work
                    HAVING COUNT(*) > 20
                    ORDER BY contract_type, total_jobs DESC;
                """;
        return executeComplexQuery(query);
    }

    private static Map<String, Integer> executeQuery(String query) {
        Map<String, Integer> result = new HashMap<>();
        try (Connection connection = DatabaseConnection.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            // Parcourir les résultats de la requête et les ajouter dans la Map
            while (rs.next()) {
                String key = rs.getString(1); // Première colonne (ex : city, activity_sector, etc.)
                int value = rs.getInt(2); // Deuxième colonne (total_jobs)
                result.put(key, value);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    // Méthode générique pour exécuter une requête complexe et retourner les
    // résultats sous forme de Map imbriquée
    private static Map<String, Map<String, Integer>> executeComplexQuery(String query) {
        Map<String, Map<String, Integer>> result = new HashMap<>();
        try (Connection connection = DatabaseConnection.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String key1 = rs.getString(1); // Première colonne (ex : required_experience, city, etc.)
                String key2 = rs.getString(2); // Deuxième colonne (ex : contract_type, remote_work, etc.)
                int value = rs.getInt(3); // Troisième colonne (total_jobs)

                result.putIfAbsent(key1, new HashMap<>());
                result.get(key1).put(key2, value);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static ArrayList<TestJob> getAllJobs() {
        ArrayList<TestJob> jobs = new ArrayList<>();
        String query = """
                SELECT city, activity_sector, required_experience, study_level, contract_type, remote_work, searched_profile, job_description
                FROM jobs
                """;
        try (Connection connection = DatabaseConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet response = statement.executeQuery(query);
            while (response.next()) {
                TestJob job = new TestJob();
                job.setCity(response.getString("city"));
                job.setActivitySector(response.getString("activity_sector"));
                job.setRequiredExperience(response.getString("required_experience"));
                job.setStudyLevel(response.getString("study_level"));
                job.setContractType(response.getString("contract_type"));
                job.setRemoteWork(response.getString("remote_work"));
                jobs.add(job);
            }
            return jobs;
        } catch (SQLException e) {
            System.out.println("Oops! something went wrong while fetching all jobs. Error: " + e.getMessage());
        }
        return null;
    }

    public static Map<String, String> getJobCountsBySite() {
        Map<String, String> jobCounts = new HashMap<>();
        
        String queryBySite = "SELECT COUNT(*) as total, site_web FROM jobs GROUP BY site_web";
        String queryTotal = "SELECT COUNT(*) as total FROM jobs";
        
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query job counts grouped by site
            try (PreparedStatement preparedStatement = connection.prepareStatement(queryBySite);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                 
                while (resultSet.next()) {
                    String siteWeb = resultSet.getString("site_web");
                    String total = resultSet.getString("total");
                    jobCounts.put(siteWeb, total);
                }
            }
    
            // Query total job count
            try (PreparedStatement totalStatement = connection.prepareStatement(queryTotal);
                 ResultSet totalResultSet = totalStatement.executeQuery()) {
                 
                if (totalResultSet.next()) {
                    String totalJobs = totalResultSet.getString("total");
                    jobCounts.put("all", totalJobs);
                }
            }
    
        } catch (Exception e) {
            System.err.println("Error while fetching job counts: " + e.getMessage());
            
        }
        
        return jobCounts;
    }

    public static List<Job> selectAll() {
        List<Job> jobs = new ArrayList<>();
        String query = """
                SELECT id, job_title, activity_sector, job_function, required_experience, 
                       study_level, contract_type, searched_profile, remote_work, city, 
                       job_description, site_web, image_url, job_page_url, entreprise, 
                       entreprise_address, publication_date, entreprise_description, 
                       soft_skills, salary, region, hard_skills, language, 
                       apply_before, language_level
                FROM jobs
                """;
                
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                Job job = new Job();
                job.setJobTitle(resultSet.getString("job_title"));
                job.setActivitySector(resultSet.getString("activity_sector"));
                job.setFunction(resultSet.getString("job_function"));
                job.setRequiredExperience(resultSet.getString("required_experience"));
                job.setStudyLevel(resultSet.getString("study_level"));
                job.setContractType(resultSet.getString("contract_type"));
                job.setSearchedProfile(resultSet.getString("searched_profile"));
                job.setRemoteWork(resultSet.getString("remote_work"));
                job.setCity(resultSet.getString("city"));
                job.setJobDescription(resultSet.getString("job_description"));
                job.setSiteWeb(resultSet.getString("site_web"));
                job.setImageUrl(resultSet.getString("image_url"));
                job.setJobPageUrl(resultSet.getString("job_page_url"));
                job.setEntreprise(resultSet.getString("entreprise"));
                job.setEntrepriseAddress(resultSet.getString("entreprise_address"));
                job.setPublicationDate(resultSet.getString("publication_date"));
                job.setEntrepriseDescription(resultSet.getString("entreprise_description"));
                job.setSoftSkills(resultSet.getString("soft_skills"));
                
                // Handle nullable salary field
                Double salary = resultSet.getDouble("salary");
                if (!resultSet.wasNull()) {
                    job.setSalary(salary);
                }
                
                job.setRegion(resultSet.getString("region"));
                job.setHardSkills(resultSet.getString("hard_skills"));
                job.setLanguage(resultSet.getString("language"));
                job.setApplyBefore(resultSet.getString("apply_before"));
                job.setLanguageLevel(resultSet.getString("language_level"));
                
                jobs.add(job);
            }
            return jobs;
            
        } catch (SQLException e) {
            System.err.println("Error while fetching all jobs: " + e.getMessage());
            return new ArrayList<>(); 
        }



       
    }
    
    public static ArrayList<TestJob> getJobsWithSalary() {
        ArrayList<TestJob> jobs = new ArrayList<>();
        String query = """
                SELECT city, activity_sector, required_experience, study_level, contract_type, remote_work, searched_profile, job_description, salary
                FROM jobs WHERE salary IS NOT NULL
                """;
        try (Connection connection = DatabaseConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet response = statement.executeQuery(query);
            while (response.next()) {
                TestJob job = new TestJob();
                job.setCity(response.getString("city"));
                job.setActivitySector(response.getString("activity_sector"));
                job.setRequiredExperience(response.getString("required_experience"));
                job.setStudyLevel(response.getString("study_level"));
                job.setContractType(response.getString("contract_type"));
                job.setRemoteWork(response.getString("remote_work"));
                job.setSalary(response.getDouble("salary"));
                jobs.add(job);
            }
            return jobs;
        } catch (SQLException e) {
            System.out.println("Oops! something went wrong while fetching all jobs. Error: " + e.getMessage());
        }
        return null;
    }
}
