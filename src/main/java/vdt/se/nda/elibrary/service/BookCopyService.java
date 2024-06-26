package vdt.se.nda.elibrary.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vdt.se.nda.elibrary.service.dto.BookCopyDTO;

/**
 * Service Interface for managing {@link vdt.se.nda.elibrary.domain.BookCopy}.
 */
public interface BookCopyService {
    /**
     * Save a bookCopy.
     *
     * @param bookCopyDTO the entity to save.
     * @return the persisted entity.
     */
    BookCopyDTO save(BookCopyDTO bookCopyDTO);

    /**
     * Updates a bookCopy.
     *
     * @param bookCopyDTO the entity to update.
     * @return the persisted entity.
     */
    BookCopyDTO update(BookCopyDTO bookCopyDTO);

    /**
     * Partially updates a bookCopy.
     *
     * @param bookCopyDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BookCopyDTO> partialUpdate(BookCopyDTO bookCopyDTO);

    /**
     * Get all the bookCopies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BookCopyDTO> findAll(Pageable pageable);

    /**
     * Get all the bookCopies with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BookCopyDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" bookCopy.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BookCopyDTO> findOne(Long id);

    /**
     * Delete the "id" bookCopy.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get the bookCopies of some book.
     *
     * @param bookId the id of the book.
     * @return the list of entities.
     */
    List<BookCopyDTO> findByBook(Long bookId);
}
