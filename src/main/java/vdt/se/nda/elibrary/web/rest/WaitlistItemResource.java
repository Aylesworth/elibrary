package vdt.se.nda.elibrary.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import vdt.se.nda.elibrary.repository.WaitlistItemRepository;
import vdt.se.nda.elibrary.service.WaitlistItemService;
import vdt.se.nda.elibrary.service.dto.WaitlistItemDTO;
import vdt.se.nda.elibrary.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vdt.se.nda.elibrary.domain.WaitlistItem}.
 */
@RestController
@RequestMapping("/api")
public class WaitlistItemResource {

    private final Logger log = LoggerFactory.getLogger(WaitlistItemResource.class);

    private static final String ENTITY_NAME = "waitlistItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WaitlistItemService waitlistItemService;

    private final WaitlistItemRepository waitlistItemRepository;

    public WaitlistItemResource(WaitlistItemService waitlistItemService, WaitlistItemRepository waitlistItemRepository) {
        this.waitlistItemService = waitlistItemService;
        this.waitlistItemRepository = waitlistItemRepository;
    }

    /**
     * {@code POST  /waitlist-items} : Create a new waitlistItem.
     *
     * @param waitlistItemDTO the waitlistItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new waitlistItemDTO, or with status {@code 400 (Bad Request)} if the waitlistItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/waitlist-items")
    public ResponseEntity<WaitlistItemDTO> createWaitlistItem(@RequestBody WaitlistItemDTO waitlistItemDTO) throws URISyntaxException {
        log.debug("REST request to save WaitlistItem : {}", waitlistItemDTO);
        if (waitlistItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new waitlistItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WaitlistItemDTO result = waitlistItemService.save(waitlistItemDTO);
        return ResponseEntity
            .created(new URI("/api/waitlist-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /waitlist-items/:id} : Updates an existing waitlistItem.
     *
     * @param id              the id of the waitlistItemDTO to save.
     * @param waitlistItemDTO the waitlistItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated waitlistItemDTO,
     * or with status {@code 400 (Bad Request)} if the waitlistItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the waitlistItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/waitlist-items/{id}")
    public ResponseEntity<WaitlistItemDTO> updateWaitlistItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WaitlistItemDTO waitlistItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update WaitlistItem : {}, {}", id, waitlistItemDTO);
        if (waitlistItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, waitlistItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!waitlistItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WaitlistItemDTO result = waitlistItemService.update(waitlistItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, waitlistItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /waitlist-items/:id} : Partial updates given fields of an existing waitlistItem, field will ignore if it is null
     *
     * @param id              the id of the waitlistItemDTO to save.
     * @param waitlistItemDTO the waitlistItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated waitlistItemDTO,
     * or with status {@code 400 (Bad Request)} if the waitlistItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the waitlistItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the waitlistItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/waitlist-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WaitlistItemDTO> partialUpdateWaitlistItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WaitlistItemDTO waitlistItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update WaitlistItem partially : {}, {}", id, waitlistItemDTO);
        if (waitlistItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, waitlistItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!waitlistItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WaitlistItemDTO> result = waitlistItemService.partialUpdate(waitlistItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, waitlistItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /waitlist-items} : get all the waitlistItems.
     *
     * @param pageable  the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of waitlistItems in body.
     */
    @GetMapping("/waitlist-items")
    public ResponseEntity<List<WaitlistItemDTO>> getAllWaitlistItems(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of WaitlistItems");
        Page<WaitlistItemDTO> page;
        if (eagerload) {
            page = waitlistItemService.findAllWithEagerRelationships(pageable);
        } else {
            page = waitlistItemService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /waitlist-items/my} : get all the waitlistItems of the current user.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of waitlistItems in body.
     */
    @GetMapping("/waitlist-items/my")
    public ResponseEntity<List<WaitlistItemDTO>> getWaitlistItemsOfCurrentUser(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of WaitlistItems");
        Page<WaitlistItemDTO> page = waitlistItemService.findByCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /waitlist-items/my/find-by-book} : get the waitlistItem of some book of the current user.
     *
     * @param bookId the id of the book.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the waitlistItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/waitlist-items/my/find-by-book")
    public ResponseEntity<WaitlistItemDTO> getWaitlistItemByBookOfCurrentUser(@RequestParam(name = "id") Long bookId) {
        log.debug("REST request to get WaitlistItem by Book : {}", bookId);
        Optional<WaitlistItemDTO> waitlistItemDTO = waitlistItemService.findByCurrentUserByBook(bookId);
        return ResponseUtil.wrapOrNotFound(waitlistItemDTO);
    }

    /**
     * {@code GET  /waitlist-items/:id} : get the "id" waitlistItem.
     *
     * @param id the id of the waitlistItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the waitlistItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/waitlist-items/{id}")
    public ResponseEntity<WaitlistItemDTO> getWaitlistItem(@PathVariable Long id) {
        log.debug("REST request to get WaitlistItem : {}", id);
        Optional<WaitlistItemDTO> waitlistItemDTO = waitlistItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(waitlistItemDTO);
    }

    /**
     * {@code DELETE  /waitlist-items/:id} : delete the "id" waitlistItem.
     *
     * @param id the id of the waitlistItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/waitlist-items/{id}")
    public ResponseEntity<Void> deleteWaitlistItem(@PathVariable Long id) {
        log.debug("REST request to delete WaitlistItem : {}", id);
        waitlistItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
