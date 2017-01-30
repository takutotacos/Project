class CreateUsers < ActiveRecord::Migration[5.0]
  def change
    create_table :users do |t|
      t.string :user_id
      t.string :email
      t.string :user_name
      t.string :password_digest
      t.binary :icon
      t.string :icon_content_type
      t.string :fb_account

      t.timestamps
    end
  end
end
