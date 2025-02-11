package com.kynsoft.notification.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.notification.application.command.ContactList.ContactListRequest;
import com.kynsoft.notification.application.command.campaign.CreateCampaignRequest;
import com.kynsoft.notification.application.command.sendMailjetEmail.SendMailJetEMailCommand;
import com.kynsoft.notification.application.command.sendMailjetEmail.SendMailJetEMailRequest;
import com.kynsoft.notification.application.command.sendMailjetEmail.SendMailjetEmailMessage;
import com.kynsoft.notification.domain.service.IEmailService;
import com.mailjet.client.errors.MailjetException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mail")
public class MailController {
    private final IEmailService emailService;
    private final IMediator mediator;

    public MailController(IMediator mediator, IEmailService emailService) {
        this.mediator = mediator;
        this.emailService = emailService;
    }

    @PostMapping("/send/email")
    public ResponseEntity<?> sendEmail(@RequestBody SendMailJetEMailRequest emailRequest) {
        SendMailJetEMailCommand sendMailJetEMailCommand = SendMailJetEMailCommand.fromRequest(emailRequest);
        SendMailjetEmailMessage sendMailjetEmailMessage = mediator.send(sendMailJetEMailCommand);
        return ResponseEntity.ok(sendMailjetEmailMessage);

    }

    @PostMapping("/create-contact-list")
    public ResponseEntity<Long> createContactList(@RequestBody ContactListRequest request) {
        try {
            Long listId = emailService.createContactList(request.getName(), request.getEmails(),
                    "516542c2b1e7bfbf3ec65077bcb619d3", "2c69809275145fcd158cf369d5676d05");
            return ResponseEntity.ok(listId);
        } catch (MailjetException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/add-contacts/{listId}")
    public ResponseEntity<String> addContactsToList(@PathVariable Long listId,
                                                    @RequestBody List<String> emails) {
        try {
            emailService.addContactsToList(listId, emails, "516542c2b1e7bfbf3ec65077bcb619d3", "2c69809275145fcd158cf369d5676d05");
            return ResponseEntity.ok("Contacts added successfully!");
        } catch (MailjetException e) {
            return ResponseEntity.status(500).body("Failed to add contacts: " + e.getMessage());
        }
    }

    @DeleteMapping("/remove-contact/{listId}")
    public ResponseEntity<String> removeContactFromList(@PathVariable Long listId,
                                                        @RequestParam String email) {
        try {
            emailService.removeContactFromList(listId, email, "516542c2b1e7bfbf3ec65077bcb619d3", "2c69809275145fcd158cf369d5676d05");
            return ResponseEntity.ok("Contact removed successfully!");
        } catch (MailjetException e) {
            return ResponseEntity.status(500).body("Failed to remove contact: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete-contact-list/{listId}")
    public ResponseEntity<String> deleteContactList(@PathVariable Long listId) {
        try {
            emailService.deleteContactList(listId, "516542c2b1e7bfbf3ec65077bcb619d3", "2c69809275145fcd158cf369d5676d05");
            return ResponseEntity.ok("Contact list deleted successfully!");
        } catch (MailjetException e) {
            return ResponseEntity.status(500).body("Failed to delete contact list: " + e.getMessage());
        }
    }

    @PostMapping("/create-campaign")
    public ResponseEntity<String> createCampaign(@RequestBody CreateCampaignRequest request) {
        try {
            String campaignId = emailService.createCampaign(request.getCampaignName(), request.getSenderEmail(),
                    request.getSenderName(), request.getSubject(), request.getTemplateId(),
                    request.getContactListId(), "516542c2b1e7bfbf3ec65077bcb619d3", "2c69809275145fcd158cf369d5676d05");
            return ResponseEntity.ok("campaign created successfully with ID: " + campaignId);
        } catch (MailjetException e) {
            return ResponseEntity.status(500).body("Failed to create campaign: " + e.getMessage());
        }
    }

    @PostMapping("/send-campaign")
    public ResponseEntity<String> sendCampaign(@RequestParam String campaignId,
                                               @RequestParam String mailjetApiKey,
                                               @RequestParam String mailjetApiSecret) {
        try {
            boolean result = emailService.sendCampaign(campaignId, mailjetApiKey, mailjetApiSecret);
            if (result) {
                return ResponseEntity.ok("campaign sent successfully!");
            } else {
                return ResponseEntity.status(500).body("Failed to send campaign");
            }
        } catch (MailjetException e) {
            return ResponseEntity.status(500).body("Failed to send campaign: " + e.getMessage());
        }
    }
}
