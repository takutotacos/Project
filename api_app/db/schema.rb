# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20170131210926) do

  create_table "posting_trn", primary_key: ["user_id", "create_date"], force: :cascade, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8" do |t|
    t.string   "user_id",     limit: 256,                                           null: false
    t.datetime "create_date",                  default: -> { "CURRENT_TIMESTAMP" }, null: false
    t.float    "latitude",    limit: 53,                                            null: false
    t.float    "longitude",   limit: 53,                                            null: false
    t.string   "comment",     limit: 256
    t.binary   "img_info",    limit: 16777215
    t.datetime "update_date"
    t.boolean  "active_flg",                   default: true
  end

  create_table "postings", force: :cascade, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8" do |t|
    t.string   "user_id",                                              null: false
    t.binary   "image",      limit: 16777215
    t.string   "comment"
    t.decimal  "latitude",                    precision: 11, scale: 8
    t.decimal  "longitude",                   precision: 11, scale: 8
    t.string   "location1"
    t.string   "location2"
    t.datetime "created_at",                                           null: false
    t.datetime "updated_at",                                           null: false
  end

  create_table "user_mst", primary_key: "user_id", id: :string, limit: 256, force: :cascade, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8" do |t|
    t.string  "email",      limit: 256,                null: false
    t.string  "password",   limit: 256,                null: false
    t.boolean "active_flg",             default: true
  end

  create_table "users", force: :cascade, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8" do |t|
    t.string   "user_id"
    t.string   "email"
    t.string   "password_digest"
    t.binary   "icon",              limit: 4294967295
    t.string   "icon_content_type"
    t.string   "fb_account",                           default: "0"
    t.datetime "created_at",                                         null: false
    t.datetime "updated_at",                                         null: false
    t.string   "password"
    t.index ["user_id"], name: "index_users_on_user_id", using: :btree
  end

end
