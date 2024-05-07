package tn.esprit.pi.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pi.entities.Publication;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.repositories.IPublicationRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pi.entities.Publication;
import tn.esprit.pi.repositories.IPublicationRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired; // Import the Autowired annotation
import org.springframework.stereotype.Service;


import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.language.v1.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;


@Slf4j

@Service
public class PublicationService implements IPublicationService {

    private final String uploadDir ="C:/xampp/htdocs/img";

            @Autowired
    IPublicationRepository publicationRepository;
    static long totalPublications = 0;

    @Override
    public Publication addPublication(Publication publication) throws IOException {
        publication.setStatus(false);
        publication.setCategory(classifyPublicationSubject(publication.getSujet()));
        return publicationRepository.save(publication);
    }

    @Override
    public Publication updatePublication(Publication publication ,long id) {
        Publication newPublication = publicationRepository.findById(id).orElse(null);
        newPublication.setSujet(newPublication.getSujet());
        newPublication.setContenu(newPublication.getContenu());
        newPublication.setPhoto(publication.getPhoto());
        return publicationRepository.save(newPublication);

    }

    @Override
    public void deletePublication(Long numPub) {
        publicationRepository.deleteById(numPub);
    }

    @Override
    public Publication getById(Long numPub) {
        return publicationRepository.findById(numPub).orElse(null);
    }

    @Override
    public List<Publication> getAll() {
        return (List<Publication>)publicationRepository.findAll();
    }

    @Override
    public List<Publication> getAllApprovedPublications() {
        Boolean status = true;
        List<Publication> approvedBlog = publicationRepository.findBlogByStatusIs(status);
        return approvedBlog;
    }

    @Override
    public List<Publication> getAllUnapprovedPublications() {
        Boolean status = false;
        List<Publication> approvedBlog = publicationRepository.findBlogByStatusIs(status);
        return approvedBlog;
    }

    //like publication
    public void likePublication(Long numPub) {
        Publication publication = getById(numPub);
        if (publication != null) { //ntestiw publication mawjouda ou non
            publication.setLikes(publication.getLikes() + 1); //incrementiw nb des likes
            publicationRepository.save(publication); //save puvlication
        }

    }

    public void unlikePublication(Long numPub) {
        Publication publication = getById(numPub);
        if (publication != null && publication.getLikes() > 0) { //ntestiw publication mawjouda ou non ou zeda nchouf ken les likes > 0
            publication.setLikes(publication.getLikes() - 1);//decrementiw nb des likes
            publicationRepository.save(publication);//save publication
        }

    }

    public Publication ApproveBlog(long id) {
        Publication blogToApprove =publicationRepository.findById(id).orElse(null);

        blogToApprove.setStatus(true);
        //sendApprovedEmail(user);
        return publicationRepository.save(blogToApprove);
    }

    public List<Publication> ApproveAllBlogs() {
        List<Publication> blogsToApprove = (List<Publication>) publicationRepository.findAll();

        for (Publication blog : blogsToApprove) {
            if (!blog.isStatus()) {
                blog.setStatus(true);
                publicationRepository.save(blog);
                //sendApprovedEmail(user);
            }
        }

        return blogsToApprove;
    }






    public void calculeMonthlyPubForStatics() {
        // Get the current month
        Month currentMonth = LocalDate.now().getMonth();
        List<Publication> allPublications = (List<Publication>) publicationRepository.findAll(); //lehne bech nejbdou publicationet lkol
        long publicationsPostedThisMonth = allPublications.stream()
                .filter(publication -> publication.getDateCreation().getMonth() == currentMonth) //nfiltriw publicationet li 3andhom meme mois
                .count(); //ncomptiw kol publication
        totalPublications += publicationsPostedThisMonth; //incrementiw nombre total ta3 publication


    }

    public void calculatetimestrePublications() {
        int trimestre = (LocalDate.now().getMonthValue() - 1) / 3 + 1;
        long publicationsThisQuarter = getAll().stream()
                .filter(publication -> publication.getDateCreation().getMonthValue() >= (trimestre * 3) &&
                        publication.getDateCreation().getMonthValue() <= trimestre * 3)
                .count();
    }

    public void calculeParanneePublications() {
        int currentYear = LocalDate.now().getYear();
        long publicationsThisYear = getAll().stream()
                .filter(publication -> publication.getDateCreation().getYear() == currentYear)
                .count();
        System.out.println("les publication favois posté cette annee: " + publicationsThisYear);
    }

    public void calculateMostActiveAuthors() {
       /* Map<User, Long> authorPublicationCount = getAll().stream()
                .collect(Collectors.groupingBy(Publication::getUser, Collectors.counting()));
        String mostActiveAuthor = String.valueOf(authorPublicationCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null));
        System.out.println("le user qui est tres active : " + mostActiveAuthor);*/
    }
    public Publication storeFile(MultipartFile file, Long id) {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String newFileName = generateNewFileName(originalFileName);

        try {
            Path filePath = Paths.get(uploadDir).resolve(newFileName);
            Files.copy(file.getInputStream(), filePath);

            Publication publication = publicationRepository.findById(id).orElse(null);
            if (publication != null) {
                publication.setPhoto(filePath.toString());
                return publicationRepository.save(publication);
            } else {
                throw new RuntimeException("Publication not found with id: " + id);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + newFileName, e);
        }
    }

    private String generateNewFileName(String originalFileName) {
        // You can customize this method to generate a unique file name.
        // For example, appending a timestamp or using a UUID.
        String timestamp = String.valueOf(System.currentTimeMillis());
        return timestamp + "_" + originalFileName;
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
            Resource resource = (Resource) new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found: " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found: " + fileName, e);
        }
    }

    /*private String classifyText(String content) throws IOException {
        // Define the path to your service account key file (replace with your actual path)
        String credentialsPath = "config/key.json";

        // Load Google Cloud credentials securely using ClassPathResource
        Resource credentialsResource = new ClassPathResource(credentialsPath);
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsResource.getInputStream());

        try {
            credentials = GoogleCredentials.fromStream(credentialsResource.getInputStream());
        } catch (IOException e) {
            log.error("Failed to load Google Cloud credentials", e);
            // Handle credential loading failure (e.g., log error, throw exception)
            throw e; // Or throw a more specific exception here
        }

        // Create a LanguageServiceClient instance
        LanguageServiceClient languageService = LanguageServiceClient.create(
                LanguageServiceSettings.newBuilder()
                        .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                        .build());

        // Create a Document object
        Document document = Document.newBuilder()
                .setContent(content)
                .setType(Document.Type.PLAIN_TEXT)
                .build();

        // Analyze the text and extract the most likely category
        AnalyzeEntitiesResponse response = languageService.analyzeEntities(document);

        String predictedCategory = null;
        float maxSalience = 0.0f;
        for (Entity entity : response.getEntitiesList()) {
            float salience = entity.getSalience();
            if (salience > maxSalience) {
                predictedCategory = entity.getName();
                maxSalience = salience;
            }
        }

        return predictedCategory;
    }*/

    private String classifyPublicationSubject(String subject) {
        Set<String> sportsKeywords = new HashSet<>(Arrays.asList(
                "football", "basketball", "tennis", "ballon", "equipe",
                "soccer", "hockey", "volleyball", "baseball", "rugby",
                "athletics", "gymnastics", "swimming", "cycling", "boxing",
                "golf", "cricket", "surfing", "skiing", "wrestling",
                "karate", "badminton", "table tennis", "archery", "rowing",
                "sailing", "karate", "martial arts", "climbing", "handball","cab","barcelona","real madrid","psg",
                "bayern","juventus","liverpool","manchester","chelsea","arsenal","milan","inter","roma","napoli","barca","realmadrid","psg",
                "bayern","juve","liverpool","manchester","chelsea","arsenal","milan","inter","roma","napoli","barca","realmadrid","psg","bayern",
                "taraji","css","ca","est","usmo","cab","barcelona","real madrid","psg","bayern","juventus","liverpool",
                "manchester","chelsea","arsenal","milan","inter","roma","napoli","barca","realmadrid","psg","bayern","juve","liverpool","manchester","chelsea","arsenal","milan","inter","roma","napoli","barca","realmadrid","psg","bayern"
        ));
        Set<String> news = new HashSet<>(Arrays.asList(
                "news", "breaking news", "latest news", "news today", "news update"
        ));

        for (String keyword : sportsKeywords) {
            if (subject.toLowerCase().contains(keyword.toLowerCase())) {
                return "Sports";
            }
        }
        for (String keyword : news) {
            if (subject.toLowerCase().contains(keyword.toLowerCase())) {
                return "News";
            }
        }
        return "Other";
    }

}











