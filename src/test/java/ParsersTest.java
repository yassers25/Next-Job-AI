
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.main.Job;
import com.parsers.rekrute.RekruteParsers;




public class ParsersTest {

    @Test
    void testParseRemoteWork() {
        // Arrange
        Job job1 = new Job();
        job1.setRemoteWork("Oui, c'est possible");
        Job job2 = new Job();
        job2.setRemoteWork("Hybride");
        Job job3 = new Job();
        job3.setRemoteWork("Non disponible");
        Job job4 = new Job(); // No remoteWork value (null)
        List<Job> jobs = Arrays.asList(job1, job2, job3, job4);
        
        // Act
        RekruteParsers.parseRemoteWork(jobs);
        
        // Assert
        assertEquals("oui", job1.getRemoteWork());
        assertEquals("hybride", job2.getRemoteWork());
        assertEquals("non", job3.getRemoteWork());
        assertEquals("non", job4.getRemoteWork());
    }

    @Test
    void testParseCity() {
        // Arrange
        List<Job> jobs = new ArrayList<>();
        
        // Test exact matches
        Job job1 = new Job();
        job1.setCity("Rabat");
        jobs.add(job1);
        
        // Test case-insensitive matches
        Job job2 = new Job();
        job2.setCity("casablanca");
        jobs.add(job2);
        
        // Test mapped cities
        Job job3 = new Job();
        job3.setCity("Sala");
        jobs.add(job3);
        
        // Test cities with accents
        Job job4 = new Job();
        job4.setCity("Tetouan");
        jobs.add(job4);
        
        // Test invalid cities
        Job job5 = new Job();
        job5.setCity("Invalid City");
        jobs.add(job5);
        
        // Test null city
        Job job6 = new Job();
        job6.setCity(null);
        jobs.add(job6);
        
        // Test remote/all cities
        Job job7 = new Job();
        job7.setCity("Remote");
        jobs.add(job7);

        // Act
        RekruteParsers.parseCity(jobs);

        // Assert
        assertEquals("rabat", jobs.get(0).getCity());
        assertEquals("casablanca", jobs.get(1).getCity());
        assertEquals("salé", jobs.get(2).getCity());
        assertEquals("tétouan", jobs.get(3).getCity());
        assertEquals(null, jobs.get(4).getCity());
        assertEquals(null, jobs.get(5).getCity());
        assertEquals("all", jobs.get(6).getCity());
    }

    @Test
    void testParseContractType() {
        // Arrange
        List<Job> jobs = new ArrayList<>();
        
        // Test exact matches
        Job job1 = new Job();
        job1.setContractType("CDI");
        jobs.add(job1);
        
        // Test case variations
        Job job2 = new Job();
        job2.setContractType("cdd");
        jobs.add(job2);
        
        // Test mapped contract types
        Job job3 = new Job();
        job3.setContractType("Free Lance");
        jobs.add(job3);
        
        // Test invalid contract types
        Job job4 = new Job();
        job4.setContractType("Invalid Contract");
        jobs.add(job4);
        
        // Test null contract type
        Job job5 = new Job();
        job5.setContractType(null);
        jobs.add(job5);
        
        // Test blank contract type
        Job job6 = new Job();
        job6.setContractType("");
        jobs.add(job6);

        // Act
        RekruteParsers.parseContractType(jobs);

        // Assert
        assertEquals("cdi", jobs.get(0).getContractType());
        assertEquals("cdd", jobs.get(1).getContractType());
        assertEquals("freelance", jobs.get(2).getContractType());
        assertEquals(null, jobs.get(3).getContractType());
        assertEquals(null, jobs.get(4).getContractType());
        assertEquals(null, jobs.get(5).getContractType());
    }

    @Test
    void testParseExperience() {
        // Arrange
        List<Job> jobs = new ArrayList<>();
        
        // Test fresh graduate
        Job job1 = new Job();
        job1.setRequiredExperience("Débutant");
        jobs.add(job1);
        
        // Test specific years
        Job job2 = new Job();
        job2.setRequiredExperience("2 ans");
        jobs.add(job2);
        
        // Test range
        Job job3 = new Job();
        job3.setRequiredExperience("4 à 5 ans");
        jobs.add(job3);
        
        // Test senior level
        Job job4 = new Job();
        job4.setRequiredExperience("8 ans");
        jobs.add(job4);
        
        // Test expert level
        Job job5 = new Job();
        job5.setRequiredExperience("12 ans");
        jobs.add(job5);
        
        // Test null experience
        Job job6 = new Job();
        job6.setRequiredExperience(null);
        jobs.add(job6);

        // Act
        RekruteParsers.parseExperience(jobs);

        // Assert
        assertEquals("fraichement diplômé", jobs.get(0).getRequiredExperience());
        assertEquals("débutant (de 1 à 3 ans)", jobs.get(1).getRequiredExperience());
        assertEquals("junior (de 3 à 5 ans)", jobs.get(2).getRequiredExperience());
        assertEquals("senior (de 5 à 10 ans)", jobs.get(3).getRequiredExperience());
        assertEquals("expert (10 ou plus)", jobs.get(4).getRequiredExperience());
        assertEquals(null, jobs.get(5).getRequiredExperience());
    }

    @Test
    void testParseStudyLevel() {
        // Arrange
        List<Job> jobs = new ArrayList<>();
        
        // Test exact matches
        Job job1 = new Job();
        job1.setStudyLevel("Bac +2");
        jobs.add(job1);
        
        // Test case variations
        Job job2 = new Job();
        job2.setStudyLevel("BAC +3");
        jobs.add(job2);
        
        // Test special cases
        Job job3 = new Job();
        job3.setStudyLevel("Doctorat");
        jobs.add(job3);
        
        // Test autodidacte
        Job job4 = new Job();
        job4.setStudyLevel("Autodidacte");
        jobs.add(job4);
        
        // Test invalid study level
        Job job5 = new Job();
        job5.setStudyLevel("Invalid Level");
        jobs.add(job5);
        
        // Test null study level
        Job job6 = new Job();
        job6.setStudyLevel(null);
        jobs.add(job6);

        // Act
        RekruteParsers.parseStudyLevel(jobs);

        // Assert
        assertEquals("bac +2", jobs.get(0).getStudyLevel());
        assertEquals("bac +3", jobs.get(1).getStudyLevel());
        assertEquals("doctorat", jobs.get(2).getStudyLevel());
        assertEquals("autodidacte", jobs.get(3).getStudyLevel());
        assertEquals(null, jobs.get(4).getStudyLevel());
        assertEquals(null, jobs.get(5).getStudyLevel());
    }

    
}
