package org.product.productinventory.repository;

import org.product.productinventory.model.Inventory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public class InventoryRepository {

    private final JdbcTemplate jdbcTemplate;


    public InventoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Inventory> getAll(){
        String sql = "SELECT * FROM inventory";
        return  jdbcTemplate.query(sql, (rs, rowNum) -> {
            Inventory inventory = new Inventory();
            inventory.setProductId(rs.getInt("product_id"));
            inventory.setAvailableQuantity(rs.getInt("available_quantity"));
            return inventory;
        });
    }

    public Optional<Inventory> findById(int productId) {
        String sql = "SELECT * FROM inventory where product_id = ?";
        try {
            Inventory inventory = jdbcTemplate.queryForObject(sql, new Object[]{productId}, (rs, rowNum) ->
                    new Inventory(rs.getInt("product_id"), rs.getInt("available_quantity"))
            );
            return Optional.ofNullable(inventory);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public int save(Inventory inventory) {
        String sql = "INSERT INTO inventory (product_id, available_quantity) VALUES(?,?)";
        System.out.println("product id.." + inventory.getProductId());
        System.out.println("AvaQna "+ inventory.getAvailableQuantity());
        return jdbcTemplate.update(sql,inventory.getProductId(), inventory.getAvailableQuantity());
    }

    public int update(Inventory inventory) {
        String sql = "UPDATE inventory SET available_quantity = ? WHERE product_id = ?";
        return jdbcTemplate.update(sql, inventory.getAvailableQuantity(), inventory.getProductId());
    }

    public int delete(int product_id) {
        String sql = "DELETE FROM inventory WHERE product_id = ?";
        return jdbcTemplate.update(sql, product_id);

    }

}
