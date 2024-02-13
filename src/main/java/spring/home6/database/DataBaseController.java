package spring.home6.database;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drop")
public class DataBaseController {

    private final DatabaseService databaseService;

    public DataBaseController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @GetMapping("/del/{nameTable}")
    public ResponseEntity<String> dropTable(@PathVariable("nameTable") String nameTable) {
                try {
            databaseService.truncateTable(nameTable);
            return ResponseEntity.ok("Table " + nameTable + " has been truncated successfully.");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error truncating table: " + e.getMessage());
        }
    }


}
