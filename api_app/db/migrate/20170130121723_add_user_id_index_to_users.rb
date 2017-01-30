class AddUserIdIndexToUsers < ActiveRecord::Migration[5.0]
  def changes
    add_index :users, :user_id, unique: true
    add_index :users, :email, unique: true
  end
end
