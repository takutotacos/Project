class AddCaetgoryIdToPosting < ActiveRecord::Migration[5.0]
  def change
    add_column :postings, :category_id, :string
  end
end
